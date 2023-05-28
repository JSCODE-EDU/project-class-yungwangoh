package com.example.yun.domain.board;

import com.example.yun.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Good {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private Good(final Member member, final Board board) {
        this.member = member;
        this.board = board;
    }

    public static Good goodCreate(Member member, Board board) {
        return new Good(member, board);
    }
}
