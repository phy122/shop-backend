package com.example.shoppingmall.product.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.shoppingmall.product.entity.Product;

public interface ProductRespository extends JpaRepository<Product, Long> {
    
    // 카테고리 별로 상품 리스트 조회 (페이징 처리)
    Page<Product> findByCategory(String category, Pageable pageable);

    // 전체 상품 리스트 조회 (페이징 처리)
    Page<Product> findAll(Pageable pageable);
}
