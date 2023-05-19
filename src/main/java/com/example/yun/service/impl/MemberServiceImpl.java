package com.example.yun.service.impl;

import com.example.yun.domain.member.Member;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.example.yun.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MemberQueryRepository memberQueryRepository;

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
    public String login(String email, String pwd) {
        return MemberService.super.login(email, pwd);
    }

    @Override
    public String logout(String email) {
        return MemberService.super.logout(email);
    }

    @Override
    public Member findMember(String email) {
        return memberQueryRepository.findMemberByEmail(email);
    }
}
