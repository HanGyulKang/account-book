package com.study.account.apis.accountBook.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.account.apis.accountBook.repository.AccountBookRepositorySupport;
import com.study.account.entity.AccountBook;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;

public class AccountBookRepositorySupportImpl extends QuerydslRepositorySupport
        implements AccountBookRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public AccountBookRepositorySupportImpl(EntityManager em) {
        super(AccountBook.class);
        this.queryFactory = new JPAQueryFactory(em);
    }
}
