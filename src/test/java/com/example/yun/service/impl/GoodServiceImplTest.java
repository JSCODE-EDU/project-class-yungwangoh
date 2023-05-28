package com.example.yun.service.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.domain.board.Good;
import com.example.yun.domain.member.Member;
import com.example.yun.exception.GoodException;
import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.board.BoardRepository;
import com.example.yun.repository.board.GoodRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.GoodQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class GoodServiceImplTest {

    @InjectMocks
    private GoodServiceImpl goodService;
    @Mock
    private GoodRepository goodRepository;
    @Mock
    private GoodQueryRepository goodQueryRepository;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private BoardRepository boardRepository;

    private Member member;
    private Board board;
    private Good good;

    private String jwt = "jwtjwtjwt";
    private Long boardId = 1L;
    private Long memberId = 1L;

    @BeforeEach
    void init() {
        member = Member.create("qwer1234@naver.com", "qwer1234@A");
        board = Board.create("gd", "gd", member);
        good = Good.goodCreate(member, board);
    }
    @Test
    @DisplayName("좋아요 증가 테스트")
    void goodUpTest() {
        // given
        given(jwtProvider.mapTokenToId(any())).willReturn(1L);
        given(memberRepository.findById(any())).willReturn(of(member));
        given(boardRepository.findById(any())).willReturn(of(board));
        given(goodRepository.save(any())).willReturn(good);

        // when
        goodService.goodUp(memberId, boardId);

        // then
        then(goodRepository).should().save(any());
    }

    @Test
    @DisplayName("좋아요 감소 실패 예외 테스트")
    void goodDownExceptionTest() {
        // given
        given(goodQueryRepository.findGoodMemberIdAndBoardId(any(), any())).willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> goodService.goodDown(memberId, boardId))
                .isInstanceOf(GoodException.class);
    }
}