package com.example.yun.util.jwt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

    public static String getFormatToken(String jwt) {
        // bearer
        return jwt.split(" ")[1];
    }
}
