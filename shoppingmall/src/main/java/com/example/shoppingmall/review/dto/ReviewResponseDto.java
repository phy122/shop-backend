package com.example.shoppingmall.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {

    private Long id;
    private Long productId;
    private int rating;
    private String comment;
    private String username;

    public ReviewResponseDto(Long id, Long productId, String username, int rating, String comment) {
        this.id = id;
        this.productId = productId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

}
