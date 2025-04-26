package com.example.shoppingmall.order.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.order.dto.OrderRequestDto;
import com.example.shoppingmall.order.dto.OrderResponseDto;
import com.example.shoppingmall.order.service.OrderService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    private Long getUserIdFromSession(HttpSession session){
        Object userId = session.getAttribute("userId");
        if(userId == null) throw new IllegalStateException("로그인이 필요합니다.");
        return (Long) userId;
    }

    @PostMapping
    public OrderResponseDto placeOrder(@RequestBody List<OrderRequestDto> orderItems, HttpSession session) {
        return orderService.placeOrder(getUserIdFromSession(session), orderItems);
    }

    @GetMapping
    public List<OrderResponseDto> getUserOrders(HttpSession session) {
        return orderService.getUserOrders(getUserIdFromSession(session));
    }
    
    
}
