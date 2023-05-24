package com.example.yun.domain.Comment;

import com.example.yun.domain.BaseEntity;
import com.example.yun.domain.board.Board;
import com.example.yun.domain.member.Member;
import com.example.yun.util.board.BoardValidationUtil;
import com.example.yun.util.member.MemberValidationUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.yun.util.member.MemberValidationUtil.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private Comment(final String content, final Member member, final Board board) {
        this.content = content;
        this.member = member;
        this.board = board;
    }

    public static Comment commentCreate(final String content, final Member member, final Board board) {

        emailValidationCheck(member.getEmail());

        return new Comment(content, member, board);
    }
}
