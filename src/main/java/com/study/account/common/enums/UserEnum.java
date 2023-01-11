package com.study.account.common.enums;

import org.hibernate.type.EnumType;

public enum UserEnum {
    SUCCESS_SIGNUP(200, "가입 성공"),
    FAILED_SIGNUP(400, "가입 실패"),
    SUCCESS_LOGOUT(204, "로그아웃 완료"),
    DUPLICATE_EMAIL(400, "이메일 주소 중복");

    private final Integer code;
    private final String message;

    UserEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
