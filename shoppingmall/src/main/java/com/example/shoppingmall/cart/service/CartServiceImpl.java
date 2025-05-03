package com.example.shoppingmall.cart.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.shoppingmall.cart.dto.CartItemRequestDto;
import com.example.shoppingmall.cart.dto.CartResponseDto;
import com.example.shoppingmall.cart.entity.Cart;
import com.example.shoppingmall.cart.entity.CartProduct;
import com.example.shoppingmall.cart.repository.CartRepository;
import com.example.shoppingmall.cart.repository.CartProductRepository;
import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.user.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;

    @Override
    public CartResponseDto getCart(User user) {
        Cart cart = cartRepository.findByUserId(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        List<CartProduct> cartProducts = cartProductRepository.findByCartId(cart.getId());

        List<CartResponseDto.CartItemDto> items = cartProducts.stream()
                .map(item -> new CartResponseDto.CartItemDto(item.getProduct().getId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new CartResponseDto(cart.getId(), items);
    }

    @Override
    public CartResponseDto addItem(User user, CartItemRequestDto requestDto) {
        Cart cart = cartRepository.findByUserId(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });

        List<CartProduct> cartProducts = cartProductRepository.findByCartId(cart.getId());

        Optional<CartProduct> existingItemOpt = cartProducts.stream()
                .filter(item -> item.getProduct().getId().equals(requestDto.getProductId()))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartProduct existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + requestDto.getQuantity());
            cartProductRepository.save(existingItem);
        } else {
            CartProduct newItem = new CartProduct();
            Product product = new Product();
            product.setId(requestDto.getProductId()); // 주의: 영속성 없으면 flush 전에 참조 불가

            newItem.setProduct(product);
            newItem.setQuantity(requestDto.getQuantity());
            newItem.setCart(cart);
            cartProductRepository.save(newItem);
        }

        List<CartProduct> updatedProducts = cartProductRepository.findByCartId(cart.getId());

        List<CartResponseDto.CartItemDto> items = updatedProducts.stream()
                .map(item -> new CartResponseDto.CartItemDto(item.getProduct().getId(), item.getQuantity()))
                .collect(Collectors.toList());

        return new CartResponseDto(cart.getId(), items);
    }

    @Override
    public void removeItem(User user, Product product) {
        Cart cart = cartRepository.findByUserId(user)
                .orElseThrow(() -> new IllegalArgumentException("장바구니가 존재하지 않습니다."));

        List<CartProduct> cartProducts = cartProductRepository.findByCartId(cart.getId());

        cartProducts.stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .ifPresent(cartProductRepository::delete);
    }
}
