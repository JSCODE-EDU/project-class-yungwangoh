package com.example.yun.jwt;

import com.example.yun.domain.member.Member;
import com.example.yun.util.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.example.yun.util.jwt.JwtUtil.getFormatToken;

@Slf4j
@Component
public class JwtProvider {

    private final Long accessExpireTime;
    private final String key;

    public JwtProvider(@Value("${jwt.key}") String key,
                       @Value("${jwt.expireTime.access}") Long accessExpireTime) {

        log.info("[key] = {}, [expireTime] = {}", key, accessExpireTime);

        this.accessExpireTime = accessExpireTime;
        this.key = JwtUtil.getSigningKey(key);
    }

    public String createToken(Member member) {
        return createTokenLogic(member, accessExpireTime);
    }

    /**
     * 토큰 생성
     * @param member 유저
     * @param expireTime 토큰 만료기간
     * @return token
     */
    private String createTokenLogic(Member member, Long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("id", member.getId());

        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public String tokenPayloadExtract(String jwt) {
        String token = getFormatToken(jwt);

        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().get("id").toString();
    }

    /**
     * 토큰 유효성, 만료 체크
     * @param jwt 토큰
     * @return 유효성 통과 true, 실패 false
     */
    public boolean tokenExpiredCheck(String jwt) {
        String token = getFormatToken(jwt);

        if(token == null || token.equals(""))
            return false;

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
}
