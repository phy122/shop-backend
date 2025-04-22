package com.example.shoppingmall.cart.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.shoppingmall.cart.dto.CartItemRequestDto;
import com.example.shoppingmall.cart.dto.CartResponseDto;
import com.example.shoppingmall.cart.entity.Cart;
import com.example.shoppingmall.cart.entity.CartItem;
import com.example.shoppingmall.cart.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    private final CartRepository cartRepository;

    // userId로 장바구니 조회, 없으면 새로 생성
    @Override
    public CartResponseDto getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });

        List<CartResponseDto.CartItemDto> items = cart.getItems().stream()
                .map(item -> new CartResponseDto.CartItemDto(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new CartResponseDto(cart.getId(), items);
    }

    // 이미 담긴 상품이면 수량 증가, 아니면 새로 추가
    @Override
    public CartResponseDto addItem(Long userId, CartItemRequestDto requestDto) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUserId(userId);
            return cartRepository.save(newCart);
        });

        Optional<CartItem> existionItem = cart.getItems().stream()
                .filter(item -> item.getProductId().equals(requestDto.getProductId()))
                .findFirst();

        if(existionItem.isPresent()){
            existionItem.get().setQuantity(existionItem.get().getQuantity() + requestDto.getQuantity());
        } else{
            CartItem item = new CartItem();
            item.setProductId(requestDto.getProductId());
            item.setQuantity(requestDto.getQuantity());
            item.setCart(cart);
            cart.getItems().add(item);
        }

        Cart savedCart = cartRepository.save(cart);

        List<CartResponseDto.CartItemDto> items = savedCart.getItems().stream()
                .map(item -> new CartResponseDto.CartItemDto(item.getProductId(), item.getQuantity()))
                .collect(Collectors.toList());
        
        return new CartResponseDto(savedCart.getId(), items);
    }

    // 특정 상품 ID를 가진 아이템을 장바구니에서 제거
    @Override
    public void removeItem(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));
        
        cart.getItems().removeIf(item -> item.getProductId().equals(productId));
        cartRepository.save(cart);
    }
    
}
