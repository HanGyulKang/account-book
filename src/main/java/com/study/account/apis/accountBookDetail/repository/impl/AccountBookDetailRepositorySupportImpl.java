package com.study.account.apis.accountBookDetail.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.account.apis.accountBookDetail.repository.AccountBookDetailRepositorySupport;
import com.study.account.entity.AccountBookDetail;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;

public class AccountBookDetailRepositorySupportImpl extends QuerydslRepositorySupport
        implements AccountBookDetailRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public AccountBookDetailRepositorySupportImpl(EntityManager em) {
        super(AccountBookDetail.class);
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }
}
