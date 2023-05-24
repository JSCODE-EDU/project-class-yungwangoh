package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.board.Board;
import com.example.yun.domain.board.Content;
import com.example.yun.domain.board.Title;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.yun.domain.board.QBoard.board;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardQueryDslRepositoryImplTest {

    @Autowired
    private EntityManager em;

    JPAQueryFactory jpaQueryFactory;

    @Nested
    @DisplayName("검색")
    class Search {

        @BeforeEach
        void init() {
            jpaQueryFactory = new JPAQueryFactory(em);
        }

        @Test
        @DisplayName("게시물을 검색할 때에 등록 시간 순으로 정렬하고 최대 100개까지 조회")
        void boardSearchBySort() {
            // given
            boardListInit();

            // when
            List<Board> boards = jpaQueryFactory.selectFrom(board)
                    .orderBy(board.createTime.desc())
                    .limit(100L)
                    .fetch();

            // then
            assertThat(boards.size()).isEqualTo(100);
        }

        @Test
        @DisplayName("게시물을 검색할 때 키워드로 검색")
        void boardSearchByKeyword() {
            // given
            String keyword = "안녕";
            boardListInit();

            // when
            List<Board> boards = jpaQueryFactory.selectFrom(board)
                    .where(board.title.title.contains(keyword))
                    .orderBy(board.createTime.desc())
                    .limit(100L)
                    .fetch();

            // then
            assertThat(boards.size()).isEqualTo(100);
        }

        void boardListInit() {
            for(int i = 0; i < 101; i++) {
                Board board = Board.create("안녕", "안녕하세요", null);
                em.persist(board);
            }
        }
    }
}