package com.example.yun.domain.board;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static com.example.yun.util.board.BoardValidationUtil.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Title {

    private String title;

    private Title(String title) {
        this.title = title;
    }

    public static Title titleCreate(String title) {
        String str = "";

        if(titleStringNullException(title)) {
            str = stringSettingTrim(title);
        }

        return new Title(str);
    }
}
