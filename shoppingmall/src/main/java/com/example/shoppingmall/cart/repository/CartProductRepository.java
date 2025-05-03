package com.example.shoppingmall.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.shoppingmall.cart.entity.CartProduct;

public interface CartProductRepository extends JpaRepository<CartProduct, Long> {

    @Query("SELECT cp FROM CartProduct cp JOIN FETCH cp.product WHERE cp.cart.id = :cartId")
    List<CartProduct> findByCartId(Long cartId);

}
