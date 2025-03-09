package com.example.deliveryapp.domain.auth.service;

import com.example.deliveryapp.global.util.JwtUtil;
import com.example.deliveryapp.global.config.security.PasswordEncoder;
import com.example.deliveryapp.domain.auth.dto.request.LoginRequest;
import com.example.deliveryapp.domain.auth.dto.request.SignupRequest;
import com.example.deliveryapp.domain.user.entity.User;
import com.example.deliveryapp.domain.user.repository.UserRepository;
import com.example.deliveryapp.global.exception.CustomException;
import com.example.deliveryapp.global.exception.ExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public void signUp(SignupRequest request) {
        validateEmailDuplication(request.getEmail());

        userRepository.save(
                User.builder()
                        .email(request.getEmail())
                        .nickname(request.getNickname())
                        .role(request.getUserRole())
                        .password(passwordEncoder.encode(request.getPassword()))
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest request) {
        User user = getUserByEmail(request.getEmail());
        validateLoginPassword(request.getPassword(), user.getPassword());
        return jwtUtil.generateToken(user.getId(), user.getRole());
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ExceptionType.INVALID_CREDENTIALS, "해당 이메일로 가입된 사용자가 없습니다."));
    }

    private void validateEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ExceptionType.DUPLICATE_EMAIL);
        }
    }

    private void validateLoginPassword(String loginPassword, String storedPassword) {
        if(!passwordEncoder.matches(loginPassword, storedPassword)) {
            throw new CustomException(ExceptionType.INVALID_CREDENTIALS);
        }
    }
}
