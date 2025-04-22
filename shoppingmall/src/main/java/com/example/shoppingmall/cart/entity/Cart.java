package com.example.shoppingmall.cart.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Cart {

    @Id // 기본키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동으로 id 값을 생성
    private Long id;

    private Long userId;    // 세션 기반 사용자 ID

    // 여러 개의 아이템을 가질 수 있다.
    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    
}
