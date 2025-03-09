package com.example.deliveryapp.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginRequest {

    @NotBlank
    @Pattern(regexp = "^[0-9A-Za-z._%+-]+@[0-9A-Za-z.-]+\\.[a-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank
    private String password;
}
