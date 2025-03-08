package com.example.deliveryapp.global.exception;

import com.example.deliveryapp.global.dto.ErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("Custom Exception [statusCode = {}, msg = {}]",
                e.getHttpStatus(), e.getMessage());

        return ResponseEntity.status(e.getHttpStatus())
                .body(ErrorResponse.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ExceptionType exceptionType = ExceptionType.REQUEST_VALIDATION_FAILED;

        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> "["+error.getField()+"] " + error.getDefaultMessage())
                .toList();

        log.error("Validation Exception [uri = {}, msg = {}]",
                e.getHeaders().getLocation(), e.getMessage());

        return ResponseEntity.status(exceptionType.getHttpStatus())
                .body(ErrorResponse.of(new CustomException(exceptionType),String.join(", ",errors)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ExceptionType exceptionType = ExceptionType.INVALID_REQUEST;

        String message = "JSON 형식이 잘못되었거나, 필드 타입이 올바르지 않습니다.";
        String fieldName = null;

        // 필드 타입이 올바르지 않을 경우
        Throwable cause = e.getCause();
        if (cause instanceof JsonMappingException mappingException) {
            List<JsonMappingException.Reference> path = mappingException.getPath();
            if(!path.isEmpty()) {
                fieldName = path.get(0).getFieldName();
                message = "wrond field: " + fieldName;
            }
        }

        return ResponseEntity.status(exceptionType.getHttpStatus())
                .body(ErrorResponse.of(new CustomException(exceptionType),message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Server Exception [msg = {}, cause = {}]",
                e.getMessage(), e.getCause());

        return ResponseEntity.internalServerError()
                .body(ErrorResponse.from(new CustomException(ExceptionType.INTERNAL_SERVER_ERROR)));
    }
}
