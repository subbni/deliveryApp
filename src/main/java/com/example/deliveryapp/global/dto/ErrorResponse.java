package com.example.deliveryapp.global.dto;

import com.example.deliveryapp.global.exception.CustomException;
import com.example.deliveryapp.global.exception.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private ExceptionType type;
    private String message;
    private String detail;

    public ErrorResponse(ExceptionType type, String message, String detail) {
        this.type = type;
        this.message = message;
        this.detail = detail;
    }

    public static ErrorResponse from(CustomException e) {
        return new ErrorResponse(e.getExceptionType(), e.getMessage(), "");
    }

    public static ErrorResponse of(CustomException e, String detail) {
        return new ErrorResponse(e.getExceptionType(), e.getMessage(), detail);
    }
}
