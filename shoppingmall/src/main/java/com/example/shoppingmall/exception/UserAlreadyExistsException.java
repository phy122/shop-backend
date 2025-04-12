package com.example.shoppingmall.exception;

// 예외 클래스(런타임 예외 사용)
public class UserAlreadyExistsException extends RuntimeException{
    
    public UserAlreadyExistsException(String username){
        // RuntimeException의 생성자 호출
        super("이미 존재하는 사용자입니다: " + username);
    }
    
}
