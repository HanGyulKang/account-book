package com.study.account.apis.accountBook.util;

import com.study.account.apis.accountBook.dto.AccountBookResponseDto;

public class ResponseUtil {

    public AccountBookResponseDto response(Integer resultCode, String resultMessage) {
        return AccountBookResponseDto
                .builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build();
    }

    public AccountBookResponseDto response(Integer resultCode, String resultMessage, Long id) {
        return AccountBookResponseDto
                .builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .id(id)
                .build();
    }
}
