package com.example.shoppingmall.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


// 예외가 발생했을 때 응답 정의
//@RestControllerAdvice(basePackages = "com.example.shoppingmall.controller") // 프로젝트 전체의 예외를 처리할 수 있는 클래스
public class GlobalExceptionHandler {
    
    @ExceptionHandler(UserAlreadyExistsException.class) // 예외가 발생했을 때 메서드 실행
    @ResponseStatus(HttpStatus.CONFLICT) // 409로 응답
    // JSON 형태로 만듦
    public Map<String, String> handleUserAlreadyExists(UserAlreadyExistsException ex){
        return Map.of("error", ex.getMessage());
    }
}
