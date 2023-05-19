package com.example.yun.util.board;

import com.example.yun.exception.StringValidationException;

public class BoardValidationUtil {

    public static String stringSettingTrim(String str) {
        return str.trim();
    }

    public static boolean titleStringNullException(String str) {
        if(str.equals("") || str.equals(" ") || titleLengthCheck(str)) {
            throw new StringValidationException("문자열 형식이 틀립니다. 공백이거나 문자열 길이를 확인해주세요.");
        }
        return true;
    }

    public static boolean contentStringNullException(String str) {
        if(str.equals("") || str.equals(" ") || contentLengthCheck(str)) {
            throw new StringValidationException("문자열 형식이 틀립니다. 공백이거나 문자열 길이를 확인해주세요.");
        }

        return true;
    }

    private static boolean contentLengthCheck(String str) {
        return str.length() > 1000;
    }

    private static boolean titleLengthCheck(String str) {
        return str.length() > 15;
    }
}
