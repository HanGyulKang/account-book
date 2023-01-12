package com.study.account.apis.accountBookDetail.dto;

import com.study.account.common.enums.PaymentTypes;
import lombok.Data;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
public class AccountBookDetailSaveDto {
    private Long accountBookId;
    private BigInteger amount;
    private String storeName;
    private String paymentTypes;
    private LocalDateTime paymentDate;

    public AccountBookDetailSaveDto(Long accountBookId, BigInteger amount, String storeName, String paymentTypes, Long paymentDate) {
        this.accountBookId = accountBookId;
        this.amount = amount;
        this.storeName = storeName;
        this.paymentTypes = paymentTypes;
        this.paymentDate = parsingUtcTimeToLocalDateTime(paymentDate);
    }

    public AccountBookDetailSaveDto(Long paymentDate) {
        this.paymentDate = parsingUtcTimeToLocalDateTime(paymentDate);
    }

    private LocalDateTime parsingUtcTimeToLocalDateTime(Long paymentDate) {
        paymentDate = paymentDate* 1000;

        Date date = new Date(paymentDate);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String formattedDate = sdf.format(date);

        return LocalDateTime.parse(formattedDate, formatter);
    }
}
