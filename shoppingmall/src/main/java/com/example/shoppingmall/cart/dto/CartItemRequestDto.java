package com.example.shoppingmall.cart.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 장바구니에 상품을 추가할 때 전송
public class CartItemRequestDto {
    private Long productId;
    private int quantity;
}
