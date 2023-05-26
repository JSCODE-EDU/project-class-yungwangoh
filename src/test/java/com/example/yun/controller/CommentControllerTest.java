package com.example.yun.controller;

import com.example.yun.dto.comment.CommentRequestDto;
import com.example.yun.repository.board.BoardRepository;
import com.example.yun.repository.member.MemberRepository;
import com.example.yun.service.BoardService;
import com.example.yun.service.CommentService;
import com.example.yun.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;


    @Test
    @Order(1)
    @DisplayName("댓글 등록 테스트")
    void commentCreateApiTest() throws Exception {
        // given
        memberService.memberCreate("qwer1234@naver.com", "qwer1234@A");
        Long boardId = boardService.boardCreate("ㅎㅇ", "ㅎㅇ", "qwer1234@naver.com").getId();

        String content = "ㅎㅇ";
        CommentRequestDto commentRequestDto = CommentRequestDto.commentRequestCreate(content);
        String s = objectMapper.writeValueAsString(commentRequestDto);

        String token = memberService.login("qwer1234@naver.com", "qwer1234@A");
        String accessToken = "bearer " + token;

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/boards/{boardId}/comment", boardId)
                .header("Authorization", accessToken)
                .content(s)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").value(content))
                .andDo(print());
    }

    @Test
    @Order(2)
    @DisplayName("게시물에 속한 댓글 모두 출력")
    void boardInCommentsOutput() throws Exception{
        // given
        Long boardId = boardService.boardCreate("ㅎㅇ", "ㅎㅇ", "qwer1234@naver.com").getId();

        String content = "ㅎㅇ";
        String token = memberService.login("qwer1234@naver.com", "qwer1234@A");
        String accessToken = "bearer " + token;

        commentService.commentCreate(boardId, accessToken, content);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/boards/{boardId}/comments", boardId)
                .header("Authorization", accessToken));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }
}