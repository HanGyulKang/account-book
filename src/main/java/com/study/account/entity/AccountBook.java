package com.study.account.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "accountBook", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private List<AccountBookDetail> accountBookDetail = new ArrayList<>();

    public void setUser(User user) {
        this.user = user;
    }

    public void modifyAmountOrMemoInAccountBook(BigInteger amount, String memo) {
        this.amount = amount;
        this.memo = memo;
    }

    public void modifyDeletedInAccountBook(Integer delete) {
        this.deleted = delete;
    }

}
