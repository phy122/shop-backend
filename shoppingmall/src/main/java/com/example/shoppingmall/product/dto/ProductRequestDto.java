package com.example.shoppingmall.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String name;
    private String description;
    private Double price;
    private String category;
}
