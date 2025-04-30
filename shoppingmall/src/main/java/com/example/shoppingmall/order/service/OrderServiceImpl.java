package com.example.shoppingmall.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.example.shoppingmall.order.dto.OrderRequestDto;
import com.example.shoppingmall.order.dto.OrderResponseDto;
import com.example.shoppingmall.order.entity.Order;
import com.example.shoppingmall.order.entity.OrderItem;
import com.example.shoppingmall.order.repository.OrderRepository;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.product.repository.ProductRespository;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor    // 생성자 자동 생성
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRespository productRespository;
    
    @Override
    public OrderResponseDto placeOrder(Long userId, List<OrderRequestDto> orderItems) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("ORDERED");

        List<OrderItem> items = orderItems.stream().map(dto -> {
            Product product = productRespository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

            OrderItem item = new OrderItem();
            item.setProduct(product);
            item.setQuantity(dto.getQuantity());
            item.setPrice(product.getPrice());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);
        Order savedOrder = orderRepository.save(order);

        return toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public OrderResponseDto getOrderDetail(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));
        
        if (!order.getUser().getId().equals(userId)) {
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
    public void deleteOrder(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("삭제 권한이 없습니다."));
        if(!order.getUser().getId().equals(userId)){
            throw new IllegalStateException("삭제 권한이 없습니다.");
        }
        orderRepository.delete(order);
    }

    private OrderResponseDto toDto(Order order){
        List<OrderResponseDto.OrderItemDto> itemDtos = order.getItems().stream()
            .map(i -> new OrderResponseDto.OrderItemDto(i.getProduct().getId(), i.getQuantity(), i.getPrice())).collect(Collectors.toList());

        return new OrderResponseDto(order.getId(), order.getOrderDate(), order.getStatus(), itemDtos);
    }
    
}
