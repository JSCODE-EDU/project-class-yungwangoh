package com.example.yun.controller;

import com.example.yun.domain.member.Member;
import com.example.yun.dto.member.MemberRequestDto;
import com.example.yun.dto.member.MemberResponseDto;
import com.example.yun.dto.member.login.LoginRequestDto;
import com.example.yun.dto.member.login.LoginResponseDto;
import com.example.yun.service.MemberService;
import com.example.yun.util.jwt.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.example.yun.dto.member.MemberResponseDto.create;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/member")
@Validated
public class MemberController {

    private final MemberService memberService;
    private final ObjectMapper objectMapper;

    @PostMapping("/create")
    public ResponseEntity<MemberResponseDto> memberCreateApi(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        Member memberCreate = memberService
                .memberCreate(memberRequestDto.getEmail(), memberRequestDto.getPassword());

        return new ResponseEntity<>(create(memberCreate), CREATED);
    }

    @GetMapping("")
    public ResponseEntity<MemberResponseDto> findMemberApi(@RequestHeader("Authorization") String jwt) throws JsonProcessingException {

        log.info("[token] = {}", jwt);

        Member jwtMember = tokenExtractMember(jwt);

        Member member = memberService.findMember(jwtMember.getId());

        return new ResponseEntity<>(create(member), OK);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) throws JsonProcessingException {

        String token = memberService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        return new ResponseEntity<>(LoginResponseDto.create(token), OK);
    }

    private Member tokenExtractMember(String token) throws JsonProcessingException {

        String extract = JwtUtil.tokenPayloadExtract(token);

        log.info("[extract] = {}", extract);

        return objectMapper.readValue(extract, Member.class);
    }
}
