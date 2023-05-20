package com.example.yun.jwt;

import com.example.yun.domain.member.Member;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.example.yun.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.naming.AuthenticationException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import static com.example.yun.util.jwt.JwtUtil.getFormatToken;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final MemberQueryRepository memberQueryRepository;
    private final ObjectMapper objectMapper;

    @Value("${jwt.key}")
    private String key;
    @Value("${jwt.expireTime.access}")
    private Long accessExpireTime;

    @PostConstruct
    void init() {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }

    public String createToken(String email) throws JsonProcessingException {
        return createTokenLogic(email, accessExpireTime);
    }

    /**
     * 토큰 생성
     * @param email 유저 이메일
     * @param expireTime 토큰 만료기간
     * @return token
     * @throws JsonProcessingException
     */
    private String createTokenLogic(String email, Long expireTime) throws JsonProcessingException {

        Member member = memberQueryRepository.findMemberByEmail(email);

        String stringObject = objectMapper.writeValueAsString(JwtObject.create(member.getId(), member.getEmail()));

        Claims claims = Jwts.claims().setSubject(stringObject);
        Date date = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    public JwtObject tokenPayloadExtract(String jwt) throws JsonProcessingException {
        String token = getFormatToken(jwt);

        String subject = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();

        return objectMapper.readValue(subject, JwtObject.class);
    }

    /**
     * 토큰 유효성, 만료 체크
     * @param jwt 토큰
     * @return 유효성 통과 true, 실패 false
     */
    public boolean tokenExpiredCheck(String jwt) throws AuthenticationException {
        String token = getFormatToken(jwt);

        if(token == null || token.equals(""))
            throw new AuthenticationException("jwt is null!!");

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
