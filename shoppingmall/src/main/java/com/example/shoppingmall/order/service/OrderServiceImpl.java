package com.example.shoppingmall.order.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.shoppingmall.order.dto.OrderRequestDto;
import com.example.shoppingmall.order.dto.OrderResponseDto;
import com.example.shoppingmall.order.entity.Order;
import com.example.shoppingmall.order.entity.OrderItem;
import com.example.shoppingmall.order.repository.OrderRepository;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.product.repository.ProductRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor    // 생성자 자동 생성
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductRespository productRespository;
    
    @Override
    public OrderResponseDto placeOrder(Long userId, List<OrderRequestDto> orderItems) {
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("ORDERED");

        List<OrderItem> items = orderItems.stream().map(dto -> {
            Product product = productRespository.findById(dto.getProductId())
                .orElseThrow(() -> new  IllegalArgumentException("상품을 찾을 수 없습니다."));
            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(dto.getQuantity());
            item.setPrice(product.getPrice());
            item.setOrder(order);
            return item;
        }).collect(Collectors.toList());

        order.setItems(items);
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDto(
            savedOrder.getId(), 
            savedOrder.getOrderDate(), 
            savedOrder.getStatus(), 
            savedOrder.getItems().stream()
                .map(i -> new OrderResponseDto.OrderItemDto(i.getProductId(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList()));
    }

    @Override
    public List<OrderResponseDto> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(order -> new OrderResponseDto(
            order.getId(), 
            order.getOrderDate(), 
            order.getStatus(), 
            order.getItems().stream()
                .map(i -> new OrderResponseDto.OrderItemDto(i.getProductId(), i.getQuantity(), i.getPrice()))
                .collect(Collectors.toList())
                )).collect(Collectors.toList());
    }
    
}
