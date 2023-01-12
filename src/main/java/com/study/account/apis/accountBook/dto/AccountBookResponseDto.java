package com.study.account.apis.accountBook.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountBookResponseDto {
    private Integer resultCode;
    private String resultMessage;
    private Long id;
}
