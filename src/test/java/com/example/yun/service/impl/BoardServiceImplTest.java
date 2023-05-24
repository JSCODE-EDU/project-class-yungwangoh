package com.example.yun.service.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.domain.member.Member;
import com.example.yun.dto.BoardRequestDto;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.dto.update.BoardContentUpdateDto;
import com.example.yun.dto.update.BoardTitleUpdateDto;
import com.example.yun.exception.BoardMessage;
import com.example.yun.repository.board.BoardRepository;
import com.example.yun.repository.querydsl.BoardQueryRepository;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    @InjectMocks
    private BoardServiceImpl boardService;
    @Mock
    private BoardRepository boardRepository;
    @Mock
    private BoardQueryRepository boardQueryRepository;
    @Mock
    private MemberQueryRepository memberQueryRepository;

    @Nested
    @DisplayName("게시물 성공")
    class Success {

        private Board board;
        private Member member;

        @BeforeEach
        void init() {
            member = Member.create("qwer1234@naver.com", "qwer1234@A");
            board = Board.create("안녕하세요", "안녕", member);
        }

        @Test
        @DisplayName("등록")
        void createBoardTest() {
            // given
            String title = "안녕하세요";
            String content = "안녕";
            String email = "qwer1234@naver.com";
            BoardRequestDto boardRequestDto = BoardRequestDto.boardRequestCreate(title, content, email);

            given(memberQueryRepository.findMemberByEmail(email)).willReturn(of(member));
            given(boardRepository.save(any())).willReturn(board);

            // when
            BoardResponseDto boardResponseDto = boardService.boardCreate(
                    boardRequestDto.getTitle(),
                    boardRequestDto.getContent(),
                    boardRequestDto.getEmail());

            // then
            assertThat(boardRequestDto.getTitle()).isEqualTo(boardResponseDto.getTitle());
            assertThat(boardRequestDto.getContent()).isEqualTo(boardResponseDto.getContent());
        }

        @Nested
        @DisplayName("수정")
        class Update {

            @Test
            @DisplayName("제목")
            void updateTitleBoardTest() {
                // given
                String title = "안녕";
                Long id = 1L;
                BoardTitleUpdateDto boardTitleUpdateDto = new BoardTitleUpdateDto(id, title);

                given(boardRepository.findById(id)).willReturn(of(board));

                // when
                BoardResponseDto boardResponseDto = boardService.boardUpdateTitle(boardTitleUpdateDto.getId(),
                        boardTitleUpdateDto.getTitle());

                // then
                assertThat(boardResponseDto.getTitle()).isEqualTo(boardTitleUpdateDto.getTitle());
            }

            @Test
            @DisplayName("내용")
            void updateContentBoardTest() {
                // given
                String content = "안녕";
                Long id = 1L;
                BoardContentUpdateDto boardContentUpdateDto = new BoardContentUpdateDto(id, content);

                given(boardRepository.findById(id)).willReturn(of(board));

                // when
                BoardResponseDto boardResponseDto = boardService.boardUpdateContent(boardContentUpdateDto.getId(),
                        boardContentUpdateDto.getContent());

                // then
                assertThat(boardResponseDto.getContent()).isEqualTo(boardContentUpdateDto.getContent());
            }
        }

        @Nested
        @DisplayName("조회")
        class Search {

            @Test
            @DisplayName("특정 게시물")
            void boardSearchTest() {
                // given
                Long id = 1L;

                given(boardRepository.findById(id)).willReturn(of(board));

                // when
                BoardResponseDto boardResponseDto = boardService.boardSearch(id);

                // then
                assertThat(boardResponseDto.getContent()).isEqualTo(board.getContent());
                assertThat(boardResponseDto.getTitle()).isEqualTo(board.getTitle());
                assertThat(boardResponseDto.getId()).isEqualTo(board.getId());
            }

            @Test
            @DisplayName("전체 게시물")
            void boardAllSearch() {
                // given
                List<Board> list = new ArrayList<>();
                list.add(board);

                given(boardRepository.findAll()).willReturn(list);

                // when
                List<BoardResponseDto> boardResponseDtos = boardService.boardAllSearch();

                // then
                assertThat(boardResponseDtos.size()).isEqualTo(list.size());
            }

            @Test
            @DisplayName("키워드로 검색한 게시물 조회")
            void boardSearchByKeyword() {
                // given
                String keyword = "안녕";

                List<Board> list = new ArrayList<>();
                list.add(board);

                given(boardQueryRepository.boardSearchByKeyword(keyword)).willReturn(list);

                // when
                List<BoardResponseDto> boardResponseDtos = boardService.boardAllSearchByKeyword(keyword);

                // then
                assertThat(boardResponseDtos.size()).isEqualTo(list.size());
                assertThat(boardResponseDtos.get(0).getTitle()).isEqualTo(board.getTitle());
                assertThat(boardResponseDtos.get(0).getContent()).isEqualTo(board.getContent());
            }
        }

        @Test
        @DisplayName("삭제")
        void boardDeleteTest() {
            // given
            Long id = 1L;

            given(boardRepository.findById(id)).willReturn(of(board));

            // when
            String boardDelete = boardService.boardDelete(id);

            // then
            assertThat(boardDelete).isEqualTo(BoardMessage.BOARD_DELETE_SUCCESS);
        }
    }

    @Nested
    @DisplayName("게시물 실패")
    class Failed {

        @Nested
        @DisplayName("공통 에러 -> 존재하지 않는 id")
        class CommonError {
            @Test
            @DisplayName("존재하지 않은 id")
            void notExistIdDelete() {
                // given
                Long id = 1L;

                given(boardRepository.findById(id)).willThrow(IllegalArgumentException.class);

                // when + then > 존재하지 않은 id를 조회할 경우 에러
                assertThrows(IllegalArgumentException.class, () -> {
                    boardService.boardSearch(id);
                });

            }
        }
    }
}