package com.example.shoppingmall.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProduct(Product product);
}
