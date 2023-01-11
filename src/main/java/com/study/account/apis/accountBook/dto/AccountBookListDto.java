package com.study.account.apis.accountBook.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.querydsl.core.util.StringUtils;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Data
public class AccountBookListDto {
    private Long accountBookId;
    private BigInteger amount;
    private String memo;
    private String deleted;
    private LocalDateTime recordDate;
    private Long accountBookDetailId;
    private String storeName;
    private String paymentTypes;
    private LocalDateTime paymentDate;

    @QueryProjection
    public AccountBookListDto(Long accountBookId, BigInteger amount, String memo
            , Integer deleted, LocalDateTime recordDate, Long accountBookDetailId
            , String storeName, String paymentTypes, LocalDateTime paymentDate) {
        // account book
        this.accountBookId = accountBookId;
        this.amount = amount;
        this.memo = memo;
        if(deleted == 0) {
            this.deleted = "정상";
        } else {
            this.deleted = "삭제됨";
        }
        this.recordDate = recordDate;

        // account book detail
        this.accountBookDetailId = accountBookDetailId;
        this.storeName = StringUtils.isNullOrEmpty(storeName) ? "-" : storeName;
        this.paymentTypes = StringUtils.isNullOrEmpty(paymentTypes) ? "-" : storeName;
        this.paymentDate = paymentDate;
    }
}
