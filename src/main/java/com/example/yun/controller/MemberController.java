package com.example.yun.controller;

import com.example.yun.custom.annotation.JwtMemberId;
import com.example.yun.domain.member.Member;
import com.example.yun.dto.member.MemberRequestDto;
import com.example.yun.dto.member.MemberResponseDto;
import com.example.yun.dto.member.login.LoginRequestDto;
import com.example.yun.dto.member.login.LoginResponseDto;
import com.example.yun.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

    @PostMapping("/create")
    public ResponseEntity<MemberResponseDto> memberCreateApi(@RequestBody @Valid MemberRequestDto memberRequestDto) {
        Member memberCreate = memberService
                .memberCreate(memberRequestDto.getEmail(), memberRequestDto.getPassword());

        return new ResponseEntity<>(create(memberCreate), CREATED);
    }

    @GetMapping("")
    public ResponseEntity<MemberResponseDto> findMemberApi(@JwtMemberId Long memberId) {

        Member member = memberService.findMember(memberId);

        return new ResponseEntity<>(create(member), OK);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {

        String token = memberService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        return new ResponseEntity<>(LoginResponseDto.create(token), OK);
    }
}
