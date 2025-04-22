package com.example.shoppingmall.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shoppingmall.exception.UserAlreadyExistsException;
import com.example.shoppingmall.user.dto.LoginDto;
import com.example.shoppingmall.user.dto.UserDto;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.user.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController // Rest API 컨트롤러 (JSON 형태로 응답)
@RequiredArgsConstructor // 자동 생성자 주입
@RequestMapping("/api/users") // 기본 URL 경로 (/api/users로 시작하는 요청 처리)
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "새로운 사용자를 등록합니다.")
    @PostMapping("/register")
    // RequestBody : JSON으로 받은 데이터를 객체로 자동 변환
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            userService.register(userDto);
            return ResponseEntity.ok("회원가입 완료");
        } catch (UserAlreadyExistsException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpSession session) {
        try {
            User user = userService.login(loginDto, session); // 로그인 시도
            session.setAttribute("userId", user.getId());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } 
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("로그아웃 성공");
    }
    
    

}
