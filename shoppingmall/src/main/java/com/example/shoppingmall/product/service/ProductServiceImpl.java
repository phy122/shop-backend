package com.example.shoppingmall.product.service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.shoppingmall.product.dto.ProductRequestDto;
import com.example.shoppingmall.product.dto.ProductResponseDto;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.product.repository.ProductRespository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRespository productRespository;

    @Override
    public List<ProductResponseDto> getAllProducts(int page, int size) {
        Page<Product> productPage = productRespository.findAll(PageRequest.of(page, size));

        // getContent() : 실제 상품을 꺼냄
        // map : Product 객체를 다른 객체로 변환
        return productPage.getContent().stream()
                          .map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory()))
                          .collect(Collectors.toList());
                            
    }

    @Override
    public List<ProductResponseDto> getProductCategory(String category, int page, int size) {
        Page<Product> productPage = productRespository.findByCategory(category, PageRequest.of(page, size));
        
        return productPage.getContent().stream()
                          .map(product -> new ProductResponseDto(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getCategory()))
                          .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setCategory(productRequestDto.getCategory());

        Product savedProduct = productRespository.save(product);

        return new ProductResponseDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getDescription(), savedProduct.getPrice(), savedProduct.getCategory());
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto productRequestDto) {
        Product product = productRespository.findById(productId)
                         .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        product.setName(productRequestDto.getName());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setCategory(productRequestDto.getCategory());

        Product updatedProduct = productRespository.save(product);

        return new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice(), updatedProduct.getCategory());

    }

    @Override
    public void deleteProduct(Long productId) {
        if(!productRespository.existsById(productId)){
            throw new IllegalArgumentException("상품을 찾을 수 없습니다.");
        }
        productRespository.deleteById(productId);
    }

}
