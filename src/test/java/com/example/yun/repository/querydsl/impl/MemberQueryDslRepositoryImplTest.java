package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.member.Member;
import com.example.yun.domain.member.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class MemberQueryDslRepositoryImplTest {

    @Autowired
    private EntityManager em;

    private JPAQueryFactory jpaQueryFactory;

    @BeforeEach
    void init() {
        jpaQueryFactory = new JPAQueryFactory(em);
    }

    @Nested
    @DisplayName("중복 검사")
    class Duplication {

        @Test
        @DisplayName("이메일 중복 체크 true 이면 중복")
        void emailDuplicationCheck () {
            // given
            String email = "qwer1234@naver.com";
            String pwd = "qwer1234@A";
            Member member = Member.create(email, pwd);

            em.persist(member);

            // when
            boolean emailCheck = jpaQueryFactory.selectFrom(QMember.member)
                    .where(QMember.member.email.email.eq(email))
                    .fetchFirst() != null;

            // then
            assertTrue(emailCheck);
        }
    }
}