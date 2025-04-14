package com.example.shoppingmall.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String category;
}
