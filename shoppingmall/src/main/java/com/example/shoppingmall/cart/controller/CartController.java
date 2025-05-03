package com.example.shoppingmall.cart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.cart.dto.CartItemRequestDto;
import com.example.shoppingmall.cart.dto.CartResponseDto;
import com.example.shoppingmall.cart.service.CartService;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.user.entity.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // 세션에서 User 객체를 생성
    private User getUserFromSession(HttpSession session) {
        Object userId = session.getAttribute("userId");
        if (userId == null) throw new IllegalStateException("로그인이 필요합니다.");

        User user = new User();
        user.setId((Long) userId);
        return user;
    }

    // 현재 로그인된 사용자의 장바구니를 반환
    @GetMapping
    public CartResponseDto getCart(HttpSession session) {
        return cartService.getCart(getUserFromSession(session));
    }

    // 상품을 장바구니에 추가
    @PostMapping
    public CartResponseDto addItem(@RequestBody CartItemRequestDto requestDto, HttpSession session) {
        return cartService.addItem(getUserFromSession(session), requestDto);
    }

    // 장바구니에서 상품 제거
    @DeleteMapping("/{productId}")
    public void removeItem(@PathVariable Long productId, HttpSession session) {
        Product product = new Product();
        product.setId(productId);
        cartService.removeItem(getUserFromSession(session), product);
    }
}
