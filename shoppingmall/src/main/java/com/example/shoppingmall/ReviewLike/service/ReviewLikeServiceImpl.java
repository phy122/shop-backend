package com.example.shoppingmall.ReviewLike.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.ReviewLike.ReviewLike;
import com.example.shoppingmall.ReviewLike.repository.ReviewLikeRepository;
import com.example.shoppingmall.review.entity.Review;
import com.example.shoppingmall.review.repository.ReviewRepository;
import com.example.shoppingmall.user.entity.User;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewLikeServiceImpl implements ReviewLikeService {

    private final ReviewLikeRepository reviewLikeRepository;
    private final ReviewRepository reviewRepository;
    
    @Override
    @Transactional
    public String likeReview(Long reviewId, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            throw new IllegalStateException("로그인이 필요합니다.");
        }

        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));

        Optional<ReviewLike> existing = reviewLikeRepository.findByUserAndReview(user, review);
        if (existing.isPresent()) {
            return "이미 공감한 리뷰입니다.";
        }

        ReviewLike like = new ReviewLike();
        like.setReview(review);
        like.setUser(user);
        reviewLikeRepository.save(like);

        return "리뷰에 공감했습니다.";
    }

    @Override
    public String unlikeReview(Long reviewId, HttpSession session) {
    }

    @Override
    public Long countReview(Long reviewId) {
    }
    
}
