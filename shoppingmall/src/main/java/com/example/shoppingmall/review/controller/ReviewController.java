package com.example.shoppingmall.review.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.review.dto.ReviewRequestDto;
import com.example.shoppingmall.review.dto.ReviewResponseDto;
import com.example.shoppingmall.review.service.ReviewService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    
    private final ReviewService reviewService;

    private Long getUserId(HttpSession session){
        Object userId = session.getAttribute("userId");
        if (userId == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }
        return (Long) userId;
    }

    @PostMapping
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto dto, HttpSession session) {
        return reviewService.createReview(getUserId(session), dto);
    }

    @GetMapping("/{productId}")
    public List<ReviewResponseDto> getReview(@PathVariable Long productId) {
        return reviewService.getReviewByProduct(productId);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId, HttpSession session){
        reviewService.deleteReview(getUserId(session), reviewId);
    }
    
}