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
public class Email {

    private String email;

    private Email(String email) {
        this.email = email;
    }

    public static Email emailCreate(String email) {
        if(emailValidationCheck(email)) {
            return new Email(email);
        }

        return null;
    }
}
