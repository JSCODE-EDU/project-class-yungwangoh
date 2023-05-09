package com.example.yun.service.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.dto.BoardRequestDto;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.dto.update.BoardContentUpdateDto;
import com.example.yun.dto.update.BoardTitleUpdateDto;
import com.example.yun.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BoardServiceImplTest {

    @InjectMocks
    private BoardServiceImpl boardService;
    @Mock
    private BoardRepository boardRepository;

    private Board board;

    @Nested
    @DisplayName("게시물")
    class Success {

        @BeforeEach
        void init() {
            Long id = 1L;
            board = new Board("안녕하세요", "안녕");
            board.setId(id);
        }

        @Test
        @DisplayName("등록")
        void createBoardTest() {
            // given
            String title = "안녕하세요";
            String content = "안녕";
            BoardRequestDto boardRequestDto = new BoardRequestDto(title, content);

            given(boardRepository.save(any())).willReturn(board);

            // when
            BoardResponseDto boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(), boardRequestDto.getContent());

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
        }

        @Test
        @DisplayName("삭제")
        void boardDeleteTest() {
            // given
            Long id = 1L;

            given(boardRepository.findById(id)).willReturn(of(board));

            // when
            boardService.boardDelete(id);
        }
    }
}