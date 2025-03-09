package com.example.deliveryapp.global.config;

import com.example.deliveryapp.global.config.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilterFilterRegistration() {
        FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(jwtFilter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
