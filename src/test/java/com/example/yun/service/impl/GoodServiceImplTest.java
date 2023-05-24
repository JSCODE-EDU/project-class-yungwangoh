package com.example.yun.service.impl;

import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.board.BoardRepository;
import com.example.yun.repository.board.GoodRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.GoodQueryRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GoodServiceImplTest {

    @InjectMocks
    private GoodServiceImpl goodService;
    @Mock
    private GoodRepository likeRepository;
    @Mock
    private GoodQueryRepository likeQueryRepository;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BoardRepository boardRepository;

    
}