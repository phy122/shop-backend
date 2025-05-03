package com.example.shoppingmall.cart.service;

import com.example.shoppingmall.cart.dto.CartItemRequestDto;
import com.example.shoppingmall.cart.dto.CartResponseDto;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.user.entity.User;

public interface CartService {
    CartResponseDto getCart(User user);
    CartResponseDto addItem(User user, CartItemRequestDto requestDto);

    void removeItem(User user, Product product);
}
