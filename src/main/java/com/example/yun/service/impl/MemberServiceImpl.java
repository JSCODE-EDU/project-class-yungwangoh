package com.example.yun.service.impl;

import com.example.yun.domain.member.Member;
import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.example.yun.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.yun.util.member.LoginCheckUtil.emailCheck;
import static com.example.yun.util.member.LoginCheckUtil.passwordCheck;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public Member memberCreate(String email, String pwd) {
        Member member = Member.create(email, pwd);

        if(memberQueryRepository.duplicatedEmailCheck(email)) {
            throw new IllegalStateException("중복된 회원 입니다.");
        }

        return memberRepository.save(member);
    }

    @Override
    public String login(String email, String pwd) throws JsonProcessingException {
        Member member = Member.create(email, pwd);

        Member findMember = memberQueryRepository.findMemberByEmail(email);

        // 검증
        if(emailCheck(member, findMember) && passwordCheck(member, findMember)) {
            String token = jwtProvider.createToken(member.getEmail());
            log.info("[token] = {}", token);

            return token;
        } else {
            return null;
        }
    }

    @Override
    public String logout(String email, String jwt) {
        return MemberService.super.logout(email, jwt);
    }

    @Override
    public Member findMember(Long id) {
        return getMember(id);
    }

    private Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
    }
}
