package com.example.shoppingmall.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.shoppingmall.cart.entity.Cart;
import com.example.shoppingmall.cart.entity.CartProduct;
import com.example.shoppingmall.cart.repository.CartProductRepository;
import com.example.shoppingmall.cart.repository.CartRepository;
import com.example.shoppingmall.order.dto.OrderResponseDto;
import com.example.shoppingmall.order.entity.Order;
import com.example.shoppingmall.order.repository.OrderRepository;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 자동 생성
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    @Override
    public OrderResponseDto placeOrder(User sessionUser, Long cartId) {
        // 사용자 검증
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 장바구니 조회
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니를 찾을 수 없습니다."));

        // 장바구니 소유자 일치 확인
        if (!cart.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("해당 장바구니는 현재 사용자에게 속하지 않습니다.");
        }

        // 장바구니에 담긴 상품 목록 조회
        List<Product> products = cartProductRepository.findByCartId(cartId).stream()
                .map(CartProduct::getProduct)
                .collect(Collectors.toList());

        if (products.isEmpty()) {
            throw new IllegalArgumentException("장바구니에 상품이 없습니다.");
        }

        // 주문 생성
        Order order = new Order();
        order.setCart(cart);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("ORDERED");

        Order savedOrder = orderRepository.save(order);

        // 응답 DTO 반환
        return toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getUserOrders(User user) {
        return orderRepository.findByCart_User(user).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderDetail(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

        if (!order.getId().equals(user)) {
            throw new IllegalStateException("접근 권한이 없습니다.");
        }
        return toDto(order);
    }

    @Override
    public void updateOrderStatus(Long orderId, String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        order.setStatus(status);
    }

    @Override
    public void deleteOrder(User user, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("삭제 권한이 없습니다."));
        if (!order.getId().equals(user)) {
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }
        orderRepository.delete(order);
    }

    private OrderResponseDto toDto(Order order) {
        List<CartProduct> cartProducts = cartProductRepository.findByCartId(order.getCart().getId());
    
        List<OrderResponseDto.OrderItemDto> itemDtos = cartProducts.stream()
                .map(cp -> new OrderResponseDto.OrderItemDto(
                        cp.getProduct().getId(),
                        cp.getQuantity(),
                        cp.getProduct().getPrice()
                ))
                .collect(Collectors.toList());
    
        return new OrderResponseDto(
                order.getId(),
                order.getOrderDate(),
                order.getStatus(),
                itemDtos
        );
    }
    
    

}
