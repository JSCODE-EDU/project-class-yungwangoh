package com.example.yun.repository.querydsl;

import com.example.yun.domain.member.Member;

import java.util.Optional;

public interface MemberQueryRepository {

    default boolean duplicatedEmailCheck(String email) {
        return false;
    }

    default Optional<Member> findMemberByEmail(String email) {
        return Optional.empty();
    }
}
