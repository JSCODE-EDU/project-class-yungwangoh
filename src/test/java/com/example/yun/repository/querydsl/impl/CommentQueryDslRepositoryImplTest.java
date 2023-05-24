package com.example.yun.repository.querydsl.impl;

import com.example.yun.domain.Comment.Comment;
import com.example.yun.domain.board.Board;
import com.example.yun.domain.member.Member;
import com.example.yun.repository.BoardRepository;
import com.example.yun.repository.comment.CommentRepository;
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

import static com.example.yun.domain.Comment.QComment.comment;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CommentQueryDslRepositoryImplTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    private JPAQueryFactory jpaQueryFactory;
    private Board boardSave;
    private Member memberSave;

    @BeforeEach
    void init() {
        jpaQueryFactory = new JPAQueryFactory(em);

        Member member = Member.create("qwer1234@naver.com", "qwer1234@A");
        memberSave = memberRepository.save(member);

        Board board = Board.create("ㅎㅇ", "ㅎㅇ", memberSave);
        boardSave = boardRepository.save(board);

    }

    @Test
    @DisplayName("게시물 id로 조회하여 댓글 리스트 출력")
    void boardIdFindOutputCommentList() {
        // given
        Comment c = Comment.commentCreate("ㅎㅇ", memberSave, boardSave);
        Comment commentSave = commentRepository.save(c);

        // when
        List<Comment> comments = jpaQueryFactory.selectFrom(comment)
                .where(comment.board.id.eq(boardSave.getId()))
                .fetch();

        // then
        assertThat(comments.size()).isEqualTo(1);
        assertThat(comments.get(0)).isEqualTo(commentSave);
    }
}