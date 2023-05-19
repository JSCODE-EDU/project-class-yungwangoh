package com.example.yun.dto.member;

import com.example.yun.util.member.MemberValidationUtil;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import static com.example.yun.util.member.MemberValidationUtil.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberRequestDto {

    @Email
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$")
    private String password;

    private MemberRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberRequestDto create(String email, String password) {
        return new MemberRequestDto(email, password);
    }
}
