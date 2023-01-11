package com.study.account.apis.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class UserResponseDto {
    private Integer resultCode;
    private String resultMessage;
}
