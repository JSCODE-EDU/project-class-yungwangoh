package com.example.yun.controller;

import com.example.yun.dto.member.MemberRequestDto;
import com.example.yun.dto.member.login.LoginRequestDto;
import com.example.yun.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("회원 가입")
    void memberCreateApiTest() throws Exception {
        // given
        String email = "qwer1234@naver.com";
        String pwd = "qwer1234@A";
        MemberRequestDto memberRequestDto = MemberRequestDto.create(email, pwd);

        String s = objectMapper.writeValueAsString(memberRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/member/create")
                .content(s)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(email))
                .andDo(print());
    }

    @Test
    @DisplayName("회원 찾기")
    void findMemberApiTest() throws Exception {
        // given
        String email = "qwer1234@naver.com";
        String pwd = "qwer1234@A";

        memberCreateInit(email, pwd);
        String token = memberService.login(email, pwd);

        String requestToken = "bearer " + token;

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/member")
                .header("Authorization", requestToken));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("로그인")
    void loginApiTest() throws Exception {
        // given
        String email = "qwer1234@naver.com";
        String pwd = "qwer1234@A";

        memberCreateInit(email, pwd);

        LoginRequestDto loginRequestDto = LoginRequestDto.create(email, pwd);

        String s = objectMapper.writeValueAsString(loginRequestDto);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/member/login")
                .content(s)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

    void memberCreateInit(String email, String pwd) {
        memberService.memberCreate(email, pwd);
    }
}