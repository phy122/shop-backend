package com.example.shoppingmall.order.service;

import java.util.List;

import com.example.shoppingmall.order.dto.OrderRequestDto;
import com.example.shoppingmall.order.dto.OrderResponseDto;

public interface OrderService {
    OrderResponseDto placeOrder(Long userId, List<OrderRequestDto> orderItems);
    List<OrderResponseDto> getUserOrders(Long userId);
    OrderResponseDto getOrderDetail(Long userId, Long orderId);
    void updateOrderStatus(Long orderId, String status);
    void deleteOrder(Long userId, Long orderId);

}
