package com.example.shoppingmall.wishlist.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.wishlist.dto.WishlistResponseDto;
import com.example.shoppingmall.wishlist.service.WishlistService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{productId}")
    public ResponseEntity<String> addToWishlist(@PathVariable Long productId, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        wishlistService.addToWishlist(userId, productId);
        return ResponseEntity.ok("찜 추가 완료");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeFromWishlist(@PathVariable Long productId, HttpSession session){
        Long userId = (Long) session.getAttribute("userId");
        wishlistService.removeFromWishlist(userId, productId);
        return ResponseEntity.ok("찜 삭제 완료");
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> getWishlist(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        return ResponseEntity.ok(wishlistService.getWishlist(userId));
    }
    
    
    
}
