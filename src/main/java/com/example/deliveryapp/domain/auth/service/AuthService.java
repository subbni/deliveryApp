package com.example.deliveryapp.domain.auth.service;

import com.example.deliveryapp.config.security.PasswordEncoder;
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


    private void validateEmailDuplication(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new CustomException(ExceptionType.DUPLICATE_EMAIL);
        }
    }

}
