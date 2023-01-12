package com.study.account.apis.accountBookDetail.util;

import com.study.account.apis.accountBookDetail.dto.AccountBookDetailResponseDto;

public class ResponseUtil {

    public AccountBookDetailResponseDto response(Integer resultCode, String resultMessage) {
        return AccountBookDetailResponseDto
                .builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .build();
    }

    public AccountBookDetailResponseDto response(Integer resultCode, String resultMessage, Long id) {
        return AccountBookDetailResponseDto
                .builder()
                .resultCode(resultCode)
                .resultMessage(resultMessage)
                .id(id)
                .build();
    }
}
