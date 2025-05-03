package com.example.shoppingmall.ReviewLike.service;

import jakarta.servlet.http.HttpSession;

public interface ReviewLikeService {
    String likeReview(Long reviewId, HttpSession session);
    String unlikeReview(Long reviewId, HttpSession session);
    Long countReview(Long reviewId);
}
