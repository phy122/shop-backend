package com.example.shoppingmall.product.service;

import java.util.List;

import com.example.shoppingmall.product.dto.ProductRequestDto;
import com.example.shoppingmall.product.dto.ProductResponseDto;

public interface ProductService {
    
    // 전체 상품 목록 조회
    List<ProductResponseDto> getAllProducts(int page, int size);

    // 카테고리 별 상품 목록 조회
    List<ProductResponseDto> getProductCategory(String category, int page, int size);

    // 상품 등록
    ProductResponseDto createProduct(ProductRequestDto productRequestDto);

    // 상품 수정
    ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto);

    // 상품 삭제
    void deleteProduct(Long productId);
}
