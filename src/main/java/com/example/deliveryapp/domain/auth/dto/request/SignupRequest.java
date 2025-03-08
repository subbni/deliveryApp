package com.example.deliveryapp.domain.auth.dto.request;

import com.example.deliveryapp.domain.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {

    @NotBlank
    @Size(max = 255, message = "이메일은 최대 255자입니다.")
    @Pattern(regexp = "^[0-9A-Za-z._%+-]+@[0-9A-Za-z.-]+\\.[a-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank
    @Size(min = 8, max = 24, message = "비밀번호는 8자 이상 24자 이하입니다.")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+=]).{8,}$\n",
            message = "비밀번호는 대소문자와 특수문자(!@#$%^&*()_+=)를 하나 이상 포함해야 합니다."
    )
    private String password;

    @NotBlank
    @Size(max = 100, message = "닉네임은 최대 100자입니다.")
    private String nickname;

    @NotBlank
    private UserRole userRole;
}