package com.example.shoppingmall.review.service;

import java.util.List;

import com.example.shoppingmall.review.dto.ReviewRequestDto;
import com.example.shoppingmall.review.dto.ReviewResponseDto;

public interface ReviewService {
    ReviewResponseDto createReview(Long userId, ReviewRequestDto dto);
    List<ReviewResponseDto> getReviewByProduct(Long productId);
    void deleteReview(Long userId, Long reviewId);
}
