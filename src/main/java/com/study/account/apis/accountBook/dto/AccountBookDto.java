package com.study.account.apis.accountBook.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class AccountBookDto {
    private BigInteger amount;
    private String memo;
}
