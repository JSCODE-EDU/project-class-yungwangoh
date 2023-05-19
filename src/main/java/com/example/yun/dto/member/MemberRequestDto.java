package com.example.yun.dto.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestDto {

    private String email;
    private String password;

    private MemberRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberRequestDto create(String email, String password) {
        return new MemberRequestDto(email, password);
    }
}
