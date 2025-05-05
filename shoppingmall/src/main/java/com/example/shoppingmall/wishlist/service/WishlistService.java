package com.example.shoppingmall.wishlist.service;

import java.util.List;

import com.example.shoppingmall.wishlist.dto.WishlistResponseDto;

public interface WishlistService {

    void addToWishlist(Long userId, Long productId);
    void removeFromWishlist(Long userId, Long productId);
    List<WishlistResponseDto> getWishlist(Long userId);
    
}
