package com.example.shoppingmall.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.exception.UserAlreadyExistsException;
import com.example.shoppingmall.user.dto.UserDto;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor    // final 필드를 자동으로 생성자 주입
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void register(UserDto userDto) {
        if(userRepository.existsByUsername(userDto.getUsername())){
            // 존재하면 예외를 발생시켜 가입을 막음
            throw new UserAlreadyExistsException(userDto.getUsername());
        }


        User user = User.builder()
                        .username(userDto.getUsername())
                        .password(passwordEncoder.encode(userDto.getPassword())) // 비밀번호 암호화
                        .email(userDto.getEmail())
                        .role("USER")
                        .build();

        userRepository.save(user);
    }
    
}
