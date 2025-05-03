package com.example.shoppingmall.cart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.cart.entity.Cart;
import com.example.shoppingmall.user.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(User user);
}
