package com.example.shoppingmall.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.order.entity.Order;
import com.example.shoppingmall.user.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByCart_User(User user);
    
}
