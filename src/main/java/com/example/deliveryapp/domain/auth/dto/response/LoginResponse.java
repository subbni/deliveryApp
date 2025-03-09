package com.example.deliveryapp.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {

    private String message;

    public LoginResponse(String message) {
        this.message = message;
    }
}
