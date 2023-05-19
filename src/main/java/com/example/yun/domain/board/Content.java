package com.example.yun.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static com.example.yun.util.board.BoardValidationUtil.contentStringNullException;
import static com.example.yun.util.board.BoardValidationUtil.stringSettingTrim;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    private String content;

    private Content(String content) {
        this.content = content;
    }

    // 정적 팩토리 메서드는 네이밍 컨벤션이 있지만 알기 쉽게 네이밍을 했다.
    public static Content contentCreate(String content) {
        String str = "";

        if(contentStringNullException(content)) {
            str = stringSettingTrim(content);
        }

        return new Content(str);
    }
}
