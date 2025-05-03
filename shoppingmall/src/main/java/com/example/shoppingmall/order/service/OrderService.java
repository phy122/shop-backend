package com.example.shoppingmall.order.service;

import java.util.List;

import com.example.shoppingmall.order.dto.OrderResponseDto;
import com.example.shoppingmall.user.entity.User;

public interface OrderService {
    OrderResponseDto placeOrder(User user, Long cartId);
    List<OrderResponseDto> getUserOrders(User user);
    OrderResponseDto getOrderDetail(User user, Long orderId);
    void updateOrderStatus(Long orderId, String status);
    void deleteOrder(User user, Long orderId);

}
