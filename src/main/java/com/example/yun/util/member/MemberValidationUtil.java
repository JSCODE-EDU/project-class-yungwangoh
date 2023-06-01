package com.example.yun.util.member;

import com.example.yun.exception.StringValidationException;

public class MemberValidationUtil {

    public static String stringSettingTrim(String str) {
        return str.trim();
    }

    public static boolean emailValidationCheck(String email) {
        if(email.equals("") || email.equals(" ") || !emailValidation(email)) {
            throw new StringValidationException("이메일 형식이 옳지 않습니다.");
        }

        return true;
    }

    public static boolean pwdValidationCheck(String password) {
        if(password.equals("") || password.equals(" ") || !pwdValidation(password)) {
            throw new StringValidationException("비밀번호는 8 ~ 15자 사이로 지정해주세요.");
        }

        return true;
    }

    private static boolean pwdValidation(String password) {
        // 비밀번호는 8 ~ 15자 사이 특수문자 하나 이상 대문자 하나 이상.
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$");
    }

    private static boolean emailValidation(String email) {
        // RFC 5322 방식.
        return email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }
}
