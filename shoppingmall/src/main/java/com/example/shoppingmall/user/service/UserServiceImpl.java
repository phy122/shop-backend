package com.example.shoppingmall.user.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.shoppingmall.exception.InvalidPasswordException;
import com.example.shoppingmall.exception.UserAlreadyExistsException;
import com.example.shoppingmall.exception.UserNotFoundException;
import com.example.shoppingmall.user.dto.LoginDto;
import com.example.shoppingmall.user.dto.UserDto;
import com.example.shoppingmall.user.entity.User;
import com.example.shoppingmall.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
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

    @Override
    public User login(LoginDto loginDto, HttpSession session) {
        // 사용자 정보 조회 (Optional : null 여부 안전하게 확인)
        Optional<User> userOptional = userRepository.findByUsername(loginDto.getUsername());

        if (userOptional.isEmpty()) {
            throw new UserNotFoundException("존재하지 않는 사용자입니다.");
        }

        User user = userOptional.get();

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        // 로그인 성공 시 세션에 사용자 정보 저장
        session.setAttribute("loginUser", user);

        return user;
    }
    
}
