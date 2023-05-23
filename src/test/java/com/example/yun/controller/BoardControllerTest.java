package com.example.yun.controller;

import com.example.yun.dto.BoardRequestDto;
import com.example.yun.dto.BoardResponseDto;
import com.example.yun.dto.update.BoardContentUpdateDto;
import com.example.yun.dto.update.BoardTitleUpdateDto;
import com.example.yun.repository.BoardRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.service.BoardService;
import com.example.yun.service.MemberService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ObjectMapper objectMapper;

    private String token = "bearer ";
    private final String headerName = "Authorization";

    private String email;

    @BeforeEach
    void memberLoginInit() {
        email = "qwer1234@naver.com";
        String pwd = "qwer1234@A";

        memberService.memberCreate(email, pwd);
        token += memberService.login(email, pwd);
    }

    @AfterEach
    void memberDbInit() {
        memberRepository.deleteAll();
    }

    @Nested
    @DisplayName("성공")
    class Success {

        @Test
        @DisplayName("등록")
        void boardCreateApiTest() throws Exception {
            // given
            String title = "안녕";
            String content = "안녕하세요";
            BoardRequestDto boardRequestDto = BoardRequestDto.boardRequestCreate(title, content, email);

            String s = objectMapper.writeValueAsString(boardRequestDto);

            // when
            ResultActions resultActions = mockMvc.perform(post("/api/boards")
                    .header(headerName, token)
                    .content(s)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON));

            // then
            resultActions.andExpect(status().isCreated())
                    .andExpect(jsonPath("$.title").value(boardRequestDto.getTitle()))
                    .andExpect(jsonPath("$.content").value(boardRequestDto.getContent()));
        }

        @Nested
        @DisplayName("조회")
        class Search {

            BoardResponseDto boardResponseDto;

            @BeforeEach
            void init() {
                BoardRequestDto boardRequestDto = BoardRequestDto.boardRequestCreate("안녕", "안녕하세요", email);

                boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                        boardRequestDto.getContent(), boardRequestDto.getEmail());
            }

            @Test
            @DisplayName("모든 게시물")
            void allSearchApiTest() throws Exception {
                // given
                int size = 1;

                // when
                ResultActions resultActions = mockMvc.perform(get("/api/boards")
                        .header(headerName, token));

                // then
                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.[0].title").value(boardResponseDto.getTitle()))
                        .andExpect(jsonPath("$.[0].content").value(boardResponseDto.getContent()))
                        .andDo(print());
            }

            @Test
            @DisplayName("특정 게시물")
            void searchApiTest() throws Exception {
                // given
                Long id = boardResponseDto.getId();

                // when
                ResultActions resultActions = mockMvc.perform(get("/api/boards/{boardId}", id)
                        .header(headerName, token));

                // then
                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.title").value(boardResponseDto.getTitle()))
                        .andExpect(jsonPath("$.content").value(boardResponseDto.getContent()));
            }

            @Test
            @DisplayName("키워드로 검색한 게시물")
            void searchBoardByKeywordApiTest() throws Exception {
                // given
                String keyword = "안녕";

                // when
                ResultActions resultActions = mockMvc.perform(get("/api/boards/keyword")
                        .header(headerName, token)
                        .param("keyword", keyword));

                // then
                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.[0].title").value(boardResponseDto.getTitle()))
                        .andExpect(jsonPath("$.[0].content").value(boardResponseDto.getContent()))
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
                BoardRequestDto boardRequestDto = BoardRequestDto.boardRequestCreate("안녕", "안녕하세요", email);

                boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                        boardRequestDto.getContent(), boardRequestDto.getEmail());
            }

            @Test
            @DisplayName("제목")
            void updateTitleApiTest() throws Exception {
                // given
                String updateTitle = "안녕";
                BoardTitleUpdateDto boardTitleUpdateDto = new BoardTitleUpdateDto(boardResponseDto.getId(), updateTitle);

                String s = objectMapper.writeValueAsString(boardTitleUpdateDto);

                // when
                ResultActions resultActions = mockMvc.perform(patch("/api/boards/title")
                        .header(headerName, token)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                // then
                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(boardTitleUpdateDto.getId()))
                        .andExpect(jsonPath("$.title").value(boardTitleUpdateDto.getTitle()));
            }

            @Test
            @DisplayName("내용")
            void updateContentApiTest() throws Exception {
                // given
                String updateContent = "안녕";
                BoardContentUpdateDto boardContentUpdateDto = new BoardContentUpdateDto(boardResponseDto.getId(), updateContent);

                String s = objectMapper.writeValueAsString(boardContentUpdateDto);

                // when
                ResultActions resultActions = mockMvc.perform(patch("/api/boards/content")
                        .header(headerName, token)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                // then
                resultActions.andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").value(boardContentUpdateDto.getId()))
                        .andExpect(jsonPath("$.content").value(boardContentUpdateDto.getContent()));
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
                BoardRequestDto boardRequestDto = BoardRequestDto.boardRequestCreate("안녕", "안녕하세요", email);

                boardResponseDto = boardService.boardCreate(boardRequestDto.getTitle(),
                        boardRequestDto.getContent(), boardRequestDto.getEmail());
            }

            @Test
            @DisplayName("게시물 삭제")
            void boardDeleteApiTest() throws Exception {
                // given
                Long id = boardResponseDto.getId();

                // when
                ResultActions resultActions = mockMvc.perform(delete("/api/boards/{boardId}", id)
                        .header(headerName, token));

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

    @Nested
    @DisplayName("실패")
    class Failed {

        @Nested
        @DisplayName("유효성 검사")
        class Validated {
            @Test
            @DisplayName("공백 or 빈칸")
            void validationEmptyFailed() throws Exception {
                // given
                BoardRequestDto boardRequestDto = BoardRequestDto.boardRequestCreate("", "", email);
                String s = objectMapper.writeValueAsString(boardRequestDto);

                // when
                ResultActions resultActions = mockMvc.perform(post("/api/boards")
                        .header(headerName, token)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                // then (expect => ConstraintViolationException) status -> 400
                resultActions.andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("request Dto id 값 누락")
            void validationNoIdFailed() throws Exception {
                // given
                BoardTitleUpdateDto boardTitleUpdateDto = new BoardTitleUpdateDto(null, "하이");
                String s = objectMapper.writeValueAsString(boardTitleUpdateDto);

                // when
                ResultActions resultActions = mockMvc.perform(patch("/api/boards/title")
                        .header(headerName, token)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                // then (expect => ConstraintViolationException) status -> 400
                resultActions.andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("요구하는 문자열 길이를 넘어가는 경우 (제목의 길이는 15자 이하이다.)")
            void validationOverLength() throws Exception {
                // given
                String overTitle = "aldfjghfjdkshgjghfdkghkjfdhgkfdgfd";
                String content = "ㅎㅇ";
                BoardRequestDto boardRequestDto = BoardRequestDto.boardRequestCreate(overTitle, content, email);
                String s = objectMapper.writeValueAsString(boardRequestDto);

                // when
                ResultActions resultActions = mockMvc.perform(post("/api/boards")
                        .header(headerName, token)
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

                // then
                resultActions.andExpect(status().isBadRequest());
            }

            @Test
            @DisplayName("키워드는 공백을 제외한 1글자 이상이여야 한다.")
            void validationKeyword() throws Exception {
                // given
                String keyword = "";

                // when
                ResultActions resultActions = mockMvc.perform(get("/api/boards/keyword")
                        .header(headerName, token)
                        .param("keyword", keyword));

                // then
                resultActions.andExpect(status().isBadRequest());
            }
        }

        @AfterEach
        void initDB() {
            boardRepository.deleteAll();
        }
    }
}