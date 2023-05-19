package com.example.yun.service.impl;

import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.example.yun.service.MemberService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @InjectMocks
    private MemberService memberService;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private MemberQueryRepository memberQueryRepository;


}