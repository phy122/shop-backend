package com.example.shoppingmall.wishlist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WishlistResponseDto {
    
    private Long productId;

    private String productName;

    private double price;

    // public WishlistResponseDto(Long productId, String productName, int price){
    //     this.productId = productId;
    //     this.productName = productName;
    //     this.price = price;
    // }
}
