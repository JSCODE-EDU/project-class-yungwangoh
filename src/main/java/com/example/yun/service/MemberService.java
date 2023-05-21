package com.example.yun.service;

import com.example.yun.domain.member.Member;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MemberService {

    default Member memberCreate(String email, String pwd) {
        return null;
    }

    default String login(String email, String pwd) {
        return null;
    }

    default String logout(String email, String jwt) {
        return null;
    }

    default Member findMember(String jwt) throws JsonProcessingException {
        return null;
    }

}
