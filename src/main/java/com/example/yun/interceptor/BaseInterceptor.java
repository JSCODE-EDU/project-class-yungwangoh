package com.example.yun.interceptor;

import com.example.yun.util.jwt.JwtUtil;
import com.example.yun.util.member.LoginCheckUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.yun.util.jwt.JwtUtil.*;

@Slf4j
@RequiredArgsConstructor
public class BaseInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[request] = {}", request);
        log.info("[response] = {}", response);

        String token = request.getHeader("Authorization");

        return tokenExpiredCheck(token);
    }
}
