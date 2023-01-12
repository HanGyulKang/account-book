package com.study.account.apis.accountBookDetail.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountBookDetailResponseDto {
    private Integer resultCode;
    private String resultMessage;
    private Long id;
}
