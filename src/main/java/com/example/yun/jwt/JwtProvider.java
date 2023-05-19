package com.example.yun.jwt;

import com.example.yun.domain.member.Member;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.example.yun.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    private final MemberQueryRepository memberQueryRepository;
    private final ObjectMapper objectMapper;

    @Value("${jwt.key}")
    private String key;

    @Value("${jwt.blackList}")
    private String blackList;

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

}
