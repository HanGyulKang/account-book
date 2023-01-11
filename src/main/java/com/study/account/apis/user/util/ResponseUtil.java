package com.study.account.apis.user.util;

import com.study.account.apis.user.dto.UserResponseDto;

public class ResponseUtil {

    public UserResponseDto response(Integer resultCode, String resultMessage) {
        return UserResponseDto
                .builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build();
    }
}
