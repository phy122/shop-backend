package com.example.shoppingmall.user.service;

import com.example.shoppingmall.user.dto.LoginDto;
import com.example.shoppingmall.user.dto.UserDto;
import com.example.shoppingmall.user.entity.User;

import jakarta.servlet.http.HttpSession;

public interface UserService {

    // 회원가입
    void register(UserDto userDto);

    // 로그인
    User login(LoginDto loginDto, HttpSession session);
}
