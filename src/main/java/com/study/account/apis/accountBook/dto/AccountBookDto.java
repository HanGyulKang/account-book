package com.study.account.apis.accountBook.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AccountBookDto {
    private Long accountBookId;
    private BigInteger amount;
    private String memo;
    private Integer deleted;
}
