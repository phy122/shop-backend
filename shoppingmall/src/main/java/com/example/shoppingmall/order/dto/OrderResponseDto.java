package com.example.shoppingmall.order.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderResponseDto {
    
    private Long orderId;
    private LocalDateTime orderDate;
    private String status;
    private List<OrderItemDto> items;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class OrderItemDto{
        private Long productId;
        private int quantity;
        private double price;
    }

}
