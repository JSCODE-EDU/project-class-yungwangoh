package com.example.yun.domain.member;

import com.example.yun.util.member.MemberValidationUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static com.example.yun.util.member.MemberValidationUtil.*;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private String password;

    private Password(String password) {
        this.password = password;
    }

    public static Password pwdCreate(String password) {
        return new Password(password);
    }
}
