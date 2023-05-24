package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.domain.board.Good;
import com.example.yun.domain.board.QBoard;
import com.example.yun.domain.board.QGood;
import com.example.yun.domain.member.Member;
import com.example.yun.domain.member.QMember;
import com.example.yun.repository.board.BoardRepository;
import com.example.yun.repository.board.GoodRepository;
import com.example.yun.repository.member.MemberRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;

import java.util.List;

import static com.example.yun.domain.board.QGood.good;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GoodQueryDslRepositoryTest {

    @Autowired
    private EntityManager em;
    private JPAQueryFactory jpaQueryFactory;
    @Autowired
    private GoodRepository goodRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;
    private Member memberSave;
    private Board boardSave;

    @BeforeEach
    void init() {
        jpaQueryFactory = new JPAQueryFactory(em);

        Member member = Member.create("qwer1234@naver.com", "qwer1234@A");
        memberSave = memberRepository.save(member);

        Board board = Board.create("gd", "gd", memberSave);
        boardSave = boardRepository.save(board);
    }

    @Test
    @DisplayName("좋아요 검증을 위한 레포 테스트")
    void goodCheckTest() {
        // given
        Good g = Good.goodCreate(memberSave, boardSave);
        goodRepository.save(g);

        // when
        boolean check = jpaQueryFactory.selectFrom(good)
                .join(good.member, QMember.member).fetchJoin()
                .join(good.board, QBoard.board).fetchJoin()
                .where(good.board.eq(boardSave).and(good.member.eq(memberSave)))
                .fetchFirst() != null;

        // then
        assertTrue(check);
    }
}