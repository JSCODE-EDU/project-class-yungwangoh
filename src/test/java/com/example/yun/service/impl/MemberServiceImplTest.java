package com.example.yun.service.impl;

import com.example.yun.domain.member.Member;
import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static java.util.Optional.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberQueryRepository memberQueryRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtProvider jwtProvider;

    @Test
    @DisplayName("회원 가입")
    void memberCreateTest() {
        // given
        String email = "qwer1234@naver.com";
        String pwd = "qwer1234@A";
        String encodePwd = "fhjdkaghfkdjshglkjfsdgfs";

        Member member = Member.create(email, pwd);

        given(memberRepository.save(any())).willReturn(member);
        given(passwordEncoder.encode(pwd)).willReturn(encodePwd);

        // when
        Member memberCreate = memberService.memberCreate(email, pwd);

        // then
        assertThat(memberCreate).isEqualTo(member);
    }

    @Test
    @DisplayName("중복된 회원 가입")
    void duplicatedMemberCreate() {
        // given
        String email = "qwer1234@naver.com";
        String pwd = "qwer1234@A";

        given(memberQueryRepository.duplicatedEmailCheck(email)).willReturn(true);

        // when

        // then
        assertThatThrownBy(() -> memberService.memberCreate(email, pwd))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("로그인 할 때 암호화 패스워드와 요청된 패스워드와 비교")
    void loginPasswordComparison() {
        // given
        String email = "qwer1234@naver.com";
        String pwd = "qwer1234@A";
        String encodedPwd = "qwer1234@A";
        String jwt = "token";

        Member member = Member.create(email, pwd);

        given(memberQueryRepository.findMemberByEmail(email)).willReturn(of(member));
        given(passwordEncoder.matches(pwd, encodedPwd)).willReturn(true);
        given(jwtProvider.createToken(member)).willReturn(jwt);

        // when
        memberService.login(email, pwd);

        // then
        then(jwtProvider).should().createToken(member);
    }
}