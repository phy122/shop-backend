package com.example.shoppingmall.cart.service;

import com.example.shoppingmall.cart.dto.CartItemRequestDto;
import com.example.shoppingmall.cart.dto.CartResponseDto;

public interface CartService {
    CartResponseDto getCart(Long userId);
    CartResponseDto addItem(Long userId, CartItemRequestDto requestDto);

    void removeItem(Long userId, Long productId);
}
