package com.example.shoppingmall.wishlist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.product.entity.Product;
import com.example.shoppingmall.product.repository.ProductRespository;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.user.repository.UserRepository;
import com.example.shoppingmall.wishlist.dto.WishlistResponseDto;
import com.example.shoppingmall.wishlist.entity.Wishlist;
import com.example.shoppingmall.wishlist.repository.WishlistRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ProductRespository productRespository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addToWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Product product = productRespository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        wishlistRepository.findByUserAndProduct(user, product).ifPresent(w -> {
            throw new IllegalArgumentException("이미 찜한 상품입니다.");
        });

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setProduct(product);
        wishlistRepository.save(wishlist);

    }

    @Override
    @Transactional
    public void removeFromWishlist(Long userId, Long productId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        Product product = productRespository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

        Wishlist wishlist = wishlistRepository.findByUserAndProduct(user, product)
                .orElseThrow(() -> new IllegalArgumentException("찜 목록에 존재하지 않습니다."));
        wishlistRepository.delete(wishlist);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WishlistResponseDto> getWishlist(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        return wishlistRepository.findByUser(user).stream()
                .map(w -> new WishlistResponseDto(w.getProduct().getId(), w.getProduct().getName(),
                        w.getProduct().getPrice()))
                .toList();
    }

}
