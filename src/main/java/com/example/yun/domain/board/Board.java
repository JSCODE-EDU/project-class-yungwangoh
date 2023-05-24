package com.example.yun.domain.board;

import com.example.yun.domain.BaseEntity;
import com.example.yun.domain.member.Member;
import com.example.yun.exception.GoodOutOfLengthException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.example.yun.exception.ExceptionControl.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    private Title title;
    private Content content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    private Long good;

    private Board(final String title, final String content, final Member member) {
        this.title = Title.titleCreate(title);
        this.content = Content.contentCreate(content);
        this.member = member;
    }

    public static Board create(final String title, final String content, final Member member) {
        return new Board(title, content, member);
    }

    public String getTitle() {
        return title.getTitle();
    }
    public String getContent() {
        return content.getContent();
    }
    public void updateTitle(Title title) {
        this.title = title;
    }
    public void updateContent(Content content) {
        this.content = content;
    }
    public void goodUp() {
        this.good++;
    }
    public void goodDown() {
        if(this.good < 0L) {
            throw new GoodOutOfLengthException(GOOD_OUT_OF_LENGTH.getMessage());
        }
        this.good--;
    }
}
