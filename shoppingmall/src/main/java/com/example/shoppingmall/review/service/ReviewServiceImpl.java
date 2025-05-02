package com.example.shoppingmall.review.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.product.repository.ProductRespository;
import com.example.shoppingmall.review.dto.ReviewRequestDto;
import com.example.shoppingmall.review.dto.ReviewResponseDto;
import com.example.shoppingmall.review.entity.Review;
import com.example.shoppingmall.review.repository.ReviewRepository;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final ProductRespository productRespository;
    private final UserRepository userRepository;
    
    @Override
    public ReviewResponseDto createReview(Long userId, ReviewRequestDto dto) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Product product = productRespository.findById(dto.getProductId())
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Review review = new Review();
        review.setUser(user);
        review.setProduct(product);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());

        Review saved = reviewRepository.save(review);

        return new ReviewResponseDto(
            saved.getId(),
            product.getId(),
            user.getUsername(),
            saved.getRating(),
            saved.getComment()
        );
    }

    @Override
    public List<ReviewResponseDto> getReviewByProduct(Long productId) {
        Product product = productRespository.findById(productId)
            .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        return reviewRepository.findByProduct(product)
            .stream()
            .map(r -> new ReviewResponseDto(r.getId(), productId, r.getUser().getUsername(), r.getRating(), r.getComment()))
            .collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
            .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다."));
        
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalStateException("본인의 리뷰만 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);
    }
    
}
