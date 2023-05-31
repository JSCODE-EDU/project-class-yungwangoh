package com.example.yun.controller;

import com.example.yun.domain.member.Member;
import com.example.yun.service.BoardService;
import com.example.yun.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PageNationMvcTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberService memberService;

    private String token;

    @BeforeEach
    void init() {
        Member member = memberService.memberCreate("qwer1234@naver.com", "qwer1234@A");
        token = memberService.login("qwer1234@naver.com", "qwer1234@A");

        for(int i = 0; i < 150; i++) {
            boardService.boardCreate("ㅎㅇ", "ㅎㅇ", member.getId());
        }
    }

    @Test
    @DisplayName("페이지 네이션 테스트 150개 게시물에서 10개 씩 15개로 나누어 지는지 확인")
    void paginationTest() throws Exception {
        // given
        String accessToken = "bearer " + token;

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/boards/page/{pageNum}", 0)
                .header("Authorization", accessToken));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.boardResponseDtos.length()").value(10))
                .andExpect(jsonPath("$.pageCount").value(15));
    }
}
