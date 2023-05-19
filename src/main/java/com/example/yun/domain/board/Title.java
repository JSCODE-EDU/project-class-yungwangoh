package com.example.yun.domain.board;

import com.example.yun.util.DomainStringValidationUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

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

        if(DomainStringValidationUtil.titleStringNullException(title)) {
            str = DomainStringValidationUtil.stringSettingTrim(title);
        }

        return new Title(str);
    }
}
