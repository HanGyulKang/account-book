package com.study.account.apis.accountBook.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AccountBookModifyDto {
    private Long accountBookId;
    private BigInteger amount;
    private String memo;
}
