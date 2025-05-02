package com.example.shoppingmall.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    
    private Long productId;
    private int rating;
    private String comment;
}
