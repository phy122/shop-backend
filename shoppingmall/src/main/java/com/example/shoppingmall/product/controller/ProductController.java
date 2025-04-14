package com.example.shoppingmall.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.product.dto.ProductRequestDto;
import com.example.shoppingmall.product.dto.ProductResponseDto;
import com.example.shoppingmall.product.service.ProductService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/product")
@RequiredArgsConstructor // final 필드 자동 주입
public class ProductController {
    

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDto> getAllProducts(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "10") int size) {
        return productService.getAllProducts(page, size);
    }

    @GetMapping("/category")
    public List<ProductResponseDto> getProductsByCategory(@RequestParam String category,
                                                          @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size) {
        return productService.getProductCategory(category, page, size);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED) // 상태 코드 201
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productRequestDto) {
        return productService.createProduct(productRequestDto);
    }

    @PostMapping("/{productId}") // @PathVariable : {productId} 값을 자동으로 바인딩 , URL 경로에 포함된 값을 메서드의 파라미터로 바인딩
    public ProductResponseDto updatedProduct(@PathVariable Long productId,@RequestBody ProductRequestDto productRequestDto) {
        return productService.updateProduct(productId, productRequestDto);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long productId){
        productService.deleteProduct(productId);
    }


    
    
    
    
}
