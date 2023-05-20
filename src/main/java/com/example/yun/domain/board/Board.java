package com.example.yun.domain.board;

import com.example.yun.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseEntity {

    @Id @GeneratedValue
    private Long id;
    private Title title;
    private Content content;

    public Board(final String title, final String content) {
        this.title = Title.titleCreate(title);
        this.content = Content.contentCreate(content);
    }

    public static Board create(final String title, final String content) {
        return new Board(title, content);
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
}
