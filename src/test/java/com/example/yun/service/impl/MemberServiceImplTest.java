package com.example.yun.service.impl;

import com.example.yun.domain.member.Member;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    private MemberServiceImpl memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberQueryRepository memberQueryRepository;

    @Test
    @DisplayName("회원 가입")
    void memberCreateTest() {
        // given
        String email = "qwer1234@naver.com";
        String pwd = "qwer1234@A";

        Member member = Member.create(email, pwd);

        given(memberRepository.save(any())).willReturn(member);

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
    @DisplayName("로그인 테스트")
    void loginTest() {
        // given

        // when

        // then

    }
}