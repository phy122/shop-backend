package com.example.shoppingmall.wishlist.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.wishlist.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Optional<Wishlist> findByUserAndProduct(User user, Product product);

    List<Wishlist> findByUser(User user);
    
    
}
