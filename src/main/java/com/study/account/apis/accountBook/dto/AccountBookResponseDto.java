package com.study.account.apis.accountBook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class AccountBookResponseDto {
    private Integer resultCode;
    private String resultMessage;
    private Long id;
}
