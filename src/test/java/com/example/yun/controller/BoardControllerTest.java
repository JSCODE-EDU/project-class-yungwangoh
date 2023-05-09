package com.example.yun.controller;

import com.example.yun.dto.BoardRequestDto;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.dto.update.BoardContentUpdateDto;
import com.example.yun.dto.update.BoardTitleUpdateDto;
import com.example.yun.repository.BoardRepository;
import com.example.yun.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @Nested
    @DisplayName("게시물")
    class Success {

        @Test
        @DisplayName("등록")
        void boardCreateApiTest() throws Exception {
            // given
            String title = "안녕";
            String content = "안녕하세요";
            BoardRequestDto boardRequestDto = new BoardRequestDto(title, content);

            String s = objectMapper.writeValueAsString(boardRequestDto);

            // when
            ResultActions resultActions = mockMvc.perform(post("/api")
                    .content(s)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isCreated())
                    .andDo(print());
        }

        @Nested
        @DisplayName("조회")
        class Search {

            BoardResponseDto boardResponseDto;

            @BeforeEach
            void init() {
                BoardRequestDto boardRequestDto = new BoardRequestDto("안녕", "안녕하세요");

                boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                        boardRequestDto.getContent());
            }

            @Test
            @DisplayName("모든 게시물")
            void allSearchApiTest() throws Exception {
                // given
                int size = 1;

                // when
                ResultActions resultActions = mockMvc.perform(get("/api"));

                // then
                resultActions.andExpect(status().isOk())
                        .andDo(print());
            }

            @Test
            @DisplayName("특정 게시물")
            void searchApiTest() throws Exception {
                // given
                Long id = boardResponseDto.getId();

                // when
                ResultActions resultActions = mockMvc.perform(get("/api/{boardId}", id));

                // then
                resultActions.andExpect(status().isOk())
                        .andDo(print());
            }

            @AfterEach
            void initDB() {
                boardRepository.deleteAll();
            }
        }

        @Nested
        @DisplayName("수정")
        class Update {
            BoardResponseDto boardResponseDto;

            @BeforeEach
            void init() {
                BoardRequestDto boardRequestDto = new BoardRequestDto("안녕", "안녕하세요");

                boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                        boardRequestDto.getContent());
            }

            @Test
            @DisplayName("제목")
            void updateTitleApiTest() throws Exception {
                // given
                String updateTitle = "안녕";
                BoardTitleUpdateDto boardTitleUpdateDto = new BoardTitleUpdateDto(boardResponseDto.getId(), updateTitle);

                String s = objectMapper.writeValueAsString(boardTitleUpdateDto);

                // when
                ResultActions resultActions = mockMvc.perform(patch("/api/title")
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                // then
                resultActions.andExpect(status().isOk())
                        .andDo(print());
            }

            @Test
            @DisplayName("내용")
            void updateContentApiTest() throws Exception {
                // given
                String updateContent = "안녕";
                BoardContentUpdateDto boardContentUpdateDto = new BoardContentUpdateDto(boardResponseDto.getId(), updateContent);

                String s = objectMapper.writeValueAsString(boardContentUpdateDto);

                // when
                ResultActions resultActions = mockMvc.perform(patch("/api/content")
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                // then
                resultActions.andExpect(status().isOk())
                        .andDo(print());
            }

            @AfterEach
            void initDB() {
                boardRepository.deleteAll();
            }
        }

        @Nested
        @DisplayName("삭제")
        class Delete {

            BoardResponseDto boardResponseDto;

            @BeforeEach
            void init() {
                BoardRequestDto boardRequestDto = new BoardRequestDto("안녕", "안녕하세요");

                boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                        boardRequestDto.getContent());
            }

            @Test
            @DisplayName("삭제")
            void boardDeleteApiTest() throws Exception {
                // given
                Long id = boardResponseDto.getId();

                // when
                ResultActions resultActions = mockMvc.perform(delete("/api/{boardId}", id));

                // then
                resultActions.andExpect(status().isNoContent())
                        .andDo(print());
            }
        }

        @AfterEach
        void initDB() {
            boardRepository.deleteAll();
        }
    }
}