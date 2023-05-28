package com.example.yun.service.impl;

import com.example.yun.domain.member.Member;
import com.example.yun.exception.ExceptionControl;
import com.example.yun.exception.LoginException;
import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.example.yun.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.yun.exception.ExceptionControl.*;
import static com.example.yun.util.member.LoginCheckUtil.emailCheck;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Member memberCreate(String email, String pwd) {
        Member member = Member.create(email, passwordEncoder.encode(pwd));

        log.info("[Member encoded pwd] = {}", member.getPassword());

        if(memberQueryRepository.duplicatedEmailCheck(email)) {
            throw new IllegalStateException("중복된 회원 입니다.");
        }

        return memberRepository.save(member);
    }

    @Override
    public String login(String email, String pwd) {
        Member member = Member.create(email, pwd);

        Member findMember = memberQueryRepository.findMemberByEmail(member.getEmail())
                .orElseThrow(NOT_FOUND_MEMBER::notFoundCreate);

        // 검증
        if(userEmailAndPwdCheck(member, findMember)) {
            String token = jwtProvider.createToken(findMember);
            log.info("[token] = {}", token);

            return token;
        } else {
            throw new LoginException(LOG_IN_FAILED.getMessage());
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

    private boolean userEmailAndPwdCheck(Member member, Member findMember) {
        return emailCheck(member, findMember) && passwordEncoder.matches(member.getPassword(), findMember.getPassword());
    }
}
