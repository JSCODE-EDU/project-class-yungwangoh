package com.example.yun.util.jwt;

import com.example.yun.domain.member.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import javax.naming.AuthenticationException;

@Slf4j
public class JwtUtil {

    private static final String key = "applicationKey";

    /**
     * 토큰 유효성, 만료 체크
     * @param jwt 토큰
     * @return 유효성 통과 true, 실패 false
     */
    public static boolean tokenExpiredCheck(String jwt) throws AuthenticationException {
        String token = getFormatToken(jwt);

        if(token == null || token.equals(""))
            throw new AuthenticationException("jwt is null!!");

        try {
            Claims claims = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();
            log.info("[expireTime] = {}", claims.getExpiration());
            log.info("[subject] = {}", claims.getSubject());
        } catch (JwtException | NullPointerException e) {
            log.error("token error!!");
            return false;
        }

        return true;
    }

    public static String tokenPayloadExtract(String jwt) {
        String token = getFormatToken(jwt);

        return Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody().getSubject();
    }

    public static String getFormatToken(String jwt) {
        // bearer
        return jwt.split(" ")[1];
    }
}
