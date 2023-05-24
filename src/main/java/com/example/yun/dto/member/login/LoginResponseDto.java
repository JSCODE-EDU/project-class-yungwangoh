package com.example.yun.dto.member.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginResponseDto {

    private String accessToken;

    private LoginResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public static LoginResponseDto create(String accessToken) {
        return new LoginResponseDto(accessToken);
    }
}
