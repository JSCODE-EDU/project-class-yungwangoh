package com.example.yun.util.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

    /**
     * 토큰 유효성, 만료 체크
     * @param key 토큰 키
     * @param jwt 토큰
     * @return 유효성 통과 true, 실패 false
     */
    public static boolean tokenExpiredCheck(String key, String jwt) {
        String token = getFormatToken(jwt);

        try {
            Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
            log.info("[expireTime] = {}", claims.getExpiration());
            log.info("[subject] = {}", claims.getSubject());
        } catch (JwtException | NullPointerException e) {
            log.error("token error!!");
            return false;
        }

        return true;
    }

    public static String getFormatToken(String jwt) {
        // bearer
        return jwt.split(" ")[1];
    }
}
