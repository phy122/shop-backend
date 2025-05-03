package com.example.shoppingmall.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.order.dto.OrderRequestDto;
import com.example.shoppingmall.order.dto.OrderResponseDto;
import com.example.shoppingmall.order.service.OrderService;
import com.example.shoppingmall.user.entity.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private Long getUserIdFromSession(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null)
            throw new IllegalStateException("로그인이 필요합니다.");
        return (Long) userId;
    }

    @PostMapping
    public OrderResponseDto placeOrder(@RequestParam Long cartId, HttpSession session) {
        Long userId = getUserIdFromSession(session);
        User user = new User(); // 임시 User 객체 생성
        user.setId(userId);
        return orderService.placeOrder(user, cartId);
    }

    @GetMapping
    public List<OrderResponseDto> getUserOrders(HttpSession session) {
        User user = new User();
        user.setId(getUserIdFromSession(session));
        return orderService.getUserOrders(user);
    }

    @GetMapping("/{orderId}")
    public OrderResponseDto getOrderDetail(@PathVariable Long orderId, HttpSession session) {
        User user = new User();
        user.setId(getUserIdFromSession(session));
        return orderService.getOrderDetail(user, orderId);
    }

    @DeleteMapping("/{orderId}")
    public void deleteOrder(@PathVariable Long orderId, HttpSession session) {
        User user = new User();
        user.setId(getUserIdFromSession(session));
        orderService.deleteOrder(user, orderId);
    }

    @PatchMapping("/{orderId}/status")
    public void updateStatus(@PathVariable Long orderId, @RequestParam String status) {
        orderService.updateOrderStatus(orderId, status);
    }

}
