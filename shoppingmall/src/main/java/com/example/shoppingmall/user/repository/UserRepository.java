package com.example.shoppingmall.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shoppingmall.user.entity.User;

@Repository // DB에 접근
public interface UserRepository extends JpaRepository<User, Long> {

    // 사용자명 중복 검사
    // 엔티티를 기준으로 작성
    public boolean existsByUsername(String username);
    public Optional<User> findByUsername(String username);

}
