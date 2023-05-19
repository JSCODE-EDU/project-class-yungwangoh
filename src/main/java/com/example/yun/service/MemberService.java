package com.example.yun.service;

import com.example.yun.domain.member.Member;

public interface MemberService {

    default Member memberCreate(String email, String pwd) {
        return null;
    }

    default String login(String email, String pwd) {
        return null;
    }

    default String logout(String email) {
        return null;
    }

    default Member findMember(String email) {
        return null;
    }

}
