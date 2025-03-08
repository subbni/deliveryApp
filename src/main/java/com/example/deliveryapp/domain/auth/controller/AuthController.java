package com.example.deliveryapp.domain.auth.controller;

import com.example.deliveryapp.domain.auth.dto.request.LoginRequest;
import com.example.deliveryapp.domain.auth.dto.request.SignupRequest;
import com.example.deliveryapp.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest request) {
        authService.signUp(request);
        return ResponseEntity.ok().build();
    }

}
