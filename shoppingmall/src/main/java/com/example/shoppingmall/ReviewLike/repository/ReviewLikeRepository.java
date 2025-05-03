package com.example.shoppingmall.ReviewLike.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.ReviewLike.ReviewLike;
import com.example.shoppingmall.review.entity.Review;
import com.example.shoppingmall.user.entity.User;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    Optional<ReviewLike> findByUserAndReview(User user, Review review);
    Long countByReview(Review review);
}
