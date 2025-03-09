package com.example.deliveryapp.global.config.filter;

import com.example.deliveryapp.domain.user.entity.UserRole;
import com.example.deliveryapp.global.util.JwtUtil;
import com.example.deliveryapp.global.exception.CustomException;
import com.example.deliveryapp.global.exception.ExceptionType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private static final List<String> EXCLUDED_URIS = List.of(
            "/api/auth/signup",
            "/api/auth/login"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (isExcludedUri(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);
            validateToken(token);

            Long userId = jwtUtil.getUserId(token);
            UserRole userRole = UserRole.valueOf(jwtUtil.getUserRole(token));

            request.setAttribute("userId", userId);
            request.setAttribute("userRole", userRole);

            filterChain.doFilter(request, response);

        } catch (CustomException e) {
            handleException(response, e);
        } catch (ExpiredJwtException e) {
            handleException(response, new CustomException(ExceptionType.EXPIRED_TOKEN));
        } catch (Exception e) {
            handleException(response, new CustomException(ExceptionType.INTERNAL_SERVER_ERROR));
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header  = request.getHeader(HttpHeaders.AUTHORIZATION);
        return jwtUtil.substringToken(header);
    }

    private void validateToken(String token) {
        if(!jwtUtil.isValidToken(token)) {
            throw new CustomException(ExceptionType.INVALID_TOKEN);
        }
    }

    private void handleException(HttpServletResponse response, CustomException e) throws IOException {
        response.setStatus(e.getHttpStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = Map.of(
                "type", e.getExceptionType(),
                "message", e.getMessage()
        );

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private boolean isExcludedUri(String requestUri) {
        return EXCLUDED_URIS.contains(requestUri);
    }
}
