package com.study.account.apis.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserDto {

    @Getter
    @Builder
    @ToString
    public static class In {
        private String email;
        private String password;
    }

    @Builder
    @Getter
    @ToString
    public static class Out {
        private Integer resultCode;
        private String resultMessage;
    }
}
