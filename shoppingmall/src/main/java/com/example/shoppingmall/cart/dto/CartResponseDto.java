package com.example.shoppingmall.cart.dto;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
// 장바구니 정보를 클라이언트에 전송
public class CartResponseDto {
    private Long cartId;
    private List<CartItemDto> items;

    public CartResponseDto(Long cartId, List<CartItemDto> items) {
        this.cartId = cartId;
        this.items = items;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class CartItemDto{
        private Long productId;
        private int quantity;
    }
}
