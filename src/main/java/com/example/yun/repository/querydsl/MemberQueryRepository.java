package com.example.yun.repository.querydsl;

import com.example.yun.domain.member.Member;

public interface MemberQueryRepository {

    default boolean duplicatedEmailCheck(String email) {
        return false;
    }

    default Member findMemberByEmail(String email) {
        return null;
    }
}
