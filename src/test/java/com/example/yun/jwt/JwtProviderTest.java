package com.example.yun.jwt;

import com.example.yun.domain.member.Member;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JwtProviderTest {

    @Test
    @DisplayName("토큰 만료 테스트 -> 만료된 토큰")
    void tokenExpireTestFalse() {
        // given
        JwtProvider jwtProvider = new JwtProvider("applicationKey", 0L);

        // when
        String token = jwtProvider.createToken(Member.create("qwer1234@naver.com", "qwer1234@A"));

        String accessToken = "bearer " + token;

        // then
        assertFalse(jwtProvider.tokenExpiredCheck(accessToken));
    }

    @Test
    @DisplayName("토큰 만료 테스트 -> 만료되지 않은 토큰")
    void tokenExpireTestTrue() {
        // given
        JwtProvider jwtProvider = new JwtProvider("applicationKey", 100000L);

        // when
        String token = jwtProvider.createToken(Member.create("qwer1234@naver.com", "qwer1234@A"));

        String accessToken = "bearer " + token;

        // then
        assertTrue(jwtProvider.tokenExpiredCheck(accessToken));
    }
}