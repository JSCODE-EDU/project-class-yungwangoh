package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.member.Member;
import com.example.yun.domain.member.QMember;
import com.example.yun.repository.querydsl.MemberQueryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.example.yun.domain.member.QMember.*;
import static java.util.Optional.*;

@Repository
@RequiredArgsConstructor
public class MemberQueryDslRepositoryImpl implements MemberQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public boolean duplicatedEmailCheck(String email) {
        return jpaQueryFactory.selectFrom(member)
                .where(member.email.email.eq(email))
                .fetchFirst() != null;
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        return ofNullable(jpaQueryFactory.selectFrom(member)
                .where(member.email.email.eq(email))
                .fetchOne());
    }
}
