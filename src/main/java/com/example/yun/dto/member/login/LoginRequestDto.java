package com.example.yun.dto.member.login;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginRequestDto {

    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$")
    private String password;

    private LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginRequestDto create(String email, String pwd) {
        return new LoginRequestDto(email, pwd);
    }
}
