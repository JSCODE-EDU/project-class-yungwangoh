package com.example.yun.util.member;

import com.example.yun.domain.member.Member;

public class LoginCheckUtil {

    public static boolean passwordCheck(Member member, Member findMember) {
        return findMember.getPassword().equals(member.getPassword());
    }

    public static boolean emailCheck(Member member, Member findMember) {
        return findMember.getEmail().equals(member.getEmail());
    }
}
