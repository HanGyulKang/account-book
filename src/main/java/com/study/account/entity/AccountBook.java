package com.study.account.entity;

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
public class AccountBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_book_id")
    private Long id;
    private BigInteger amount;
    private String memo;
    private Integer deleted;
    private LocalDateTime recordDate;
    @ManyToOne
    @JoinColumn(name = "uuid")
    private User user;
    @OneToOne
    @JoinColumn(name = "account_book_detail_id")
    private AccountBookDetail accountBookDetail;

}
