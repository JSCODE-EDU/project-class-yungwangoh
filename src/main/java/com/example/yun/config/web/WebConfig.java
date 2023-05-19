package com.example.yun.config.web;

import com.example.yun.interceptor.BaseInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${origin.address}")
    private String origin;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(origin)
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3000L);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new BaseInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/member/create", "/api/member/login");
    }
}
