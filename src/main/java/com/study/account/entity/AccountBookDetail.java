package com.study.account.entity;

import com.study.account.common.enums.PaymentTypes;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AccountBookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_book_detail_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_book_id")
    private AccountBook accountBook;
    private BigInteger amount;
    private String storeName;
    @Enumerated(EnumType.STRING)
    private PaymentTypes paymentTypes;
    private LocalDateTime paymentDate;
}
