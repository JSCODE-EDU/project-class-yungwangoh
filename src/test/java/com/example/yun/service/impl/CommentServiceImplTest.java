package com.example.yun.service.impl;

import com.example.yun.domain.Comment.Comment;
import com.example.yun.domain.board.Board;
import com.example.yun.domain.member.Member;
import com.example.yun.dto.comment.CommentResponseDto;
import com.example.yun.exception.NotFoundBoardException;
import com.example.yun.exception.NotFoundMemberException;
import com.example.yun.jwt.JwtProvider;
import com.example.yun.repository.board.BoardRepository;
import com.example.yun.repository.comment.CommentRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.repository.querydsl.CommentQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.example.yun.exception.ExceptionControl.*;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @InjectMocks
    private CommentServiceImpl commentService;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private CommentQueryRepository commentQueryRepository;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private JwtProvider jwtProvider;

    String email = "qwer1234@naver.com";
    String pwd = "qwer1234@A";
    Member member = Member.create(email, pwd);
    Board board = Board.create("ㅎㅇ", "ㅎㅇ", member);
    Comment comment = Comment.commentCreate("ㅎㅇ", member, board);

    @Nested
    @DisplayName("성공")
    class Success {

        @Test
        @DisplayName("등록")
        void commentCreate() {
            // given
            given(jwtProvider.mapTokenToId(any())).willReturn(1L);
            given(memberRepository.findById(any())).willReturn(of(member));
            given(boardRepository.findById(any())).willReturn(of(board));
            given(commentRepository.save(any())).willReturn(comment);

            // when
            CommentResponseDto commentCreate = commentService.commentCreate(2L, "gfdsgs", "ㅎㅇ");

            // then
            assertThat(commentCreate.getContent()).isEqualTo("ㅎㅇ");
        }

        @Nested
        @DisplayName("조회")
        class Search {

            @Test
            @DisplayName("게시물에 있는 댓글 모두 조회")
            void findComments() {
                // given
                List<Comment> commentList = List.of(comment);

                given(boardRepository.findById(any())).willReturn(of(board));
                given(commentQueryRepository.findCommentsByBoardId(any())).willReturn(commentList);

                // when
                List<CommentResponseDto> comments = commentService.findCommentsBoardById(any());

                // then
                assertThat(commentList.get(0).getContent()).isEqualTo(comments.get(0).getContent());
            }
        }
    }

    @Nested
    @DisplayName("실패")
    class Failed {

        @Nested
        @DisplayName("등록")
        class Create {

            @Test
            @DisplayName("등록할 때 회원을 찾지 못한 경우 예외")
            void commentCreateNotFoundMember() {
                // given
                given(memberRepository.findById(any())).willThrow(new NotFoundMemberException(NOT_FOUND_MEMBER.getMessage()));

                // when

                // then
                assertThatThrownBy(() -> commentService.commentCreate(board.getId(), "gdfgsfdg", "ㅎㅇ"))
                        .isInstanceOf(NotFoundMemberException.class)
                        .hasMessageContaining(NOT_FOUND_MEMBER.getMessage());
            }

            @Test
            @DisplayName("등록할 때 게시물을 찾지 못한 경우 예외")
            void commentCreateNotFoundBoard() {
                // given
                given(memberRepository.findById(any())).willReturn(of(member));
                given(boardRepository.findById(any())).willThrow(new NotFoundBoardException(NOT_FOUND_BOARD.getMessage()));

                // when

                // then
                assertThatThrownBy(() -> commentService.commentCreate(null, "gfdgdfgg","ㅎㅇ"))
                        .isInstanceOf(NotFoundBoardException.class)
                        .hasMessageContaining(NOT_FOUND_BOARD.getMessage());

            }

        }

        @Nested
        @DisplayName("조회")
        class Search {

            @Test
            @DisplayName("조회할 때 게시물 id 값이 다른 경우")
            void findCommentWrongBoardId() {
                // given

                // when

                // then
                assertThatThrownBy(() -> commentService.findCommentsBoardById(100L))
                        .isInstanceOf(NotFoundBoardException.class)
                        .hasMessageContaining(NOT_FOUND_BOARD.getMessage());
            }
        }
    }
}