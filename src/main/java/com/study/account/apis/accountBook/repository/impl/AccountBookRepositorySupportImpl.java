package com.study.account.apis.accountBook.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.account.apis.accountBook.dto.AccountBookListDto;
import com.study.account.apis.accountBook.dto.QAccountBookListDto;
import com.study.account.apis.accountBook.repository.AccountBookRepositorySupport;
import com.study.account.common.util.SecurityUtil;
import com.study.account.entity.AccountBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.study.account.entity.QAccountBook.accountBook;
import static com.study.account.entity.QAccountBookDetail.accountBookDetail;
import static com.study.account.entity.QUser.user;

public class AccountBookRepositorySupportImpl extends QuerydslRepositorySupport
        implements AccountBookRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public AccountBookRepositorySupportImpl(EntityManager em) {
        super(AccountBook.class);
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<AccountBookListDto> findAccountBookByUuid(Pageable pageable) {
        Long userId = SecurityUtil.getUserId();

        List<AccountBookListDto> results = queryFactory
                .select(new QAccountBookListDto(
                        accountBook.id.as("accountBookId"),
                        accountBook.amount.as("amount"),
                        accountBook.memo.as("memo"),
                        accountBook.deleted.as("deleted"),
                        accountBook.recordDate.as("recordDate"),
                        accountBookDetail.id.as("accountBookDetailId"),
                        accountBookDetail.storeName.as("storeName"),
                        accountBookDetail.paymentTypes.stringValue().as("paymentTypes"),
                        accountBookDetail.paymentDate.as("paymentDate")
                ))
                .from(accountBook)
                .innerJoin(accountBook.user, user)
                .leftJoin(accountBook.accountBookDetail, accountBookDetail)
                .where(
                        user.id.eq(userId),
                        accountBook.deleted.eq(0)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<AccountBook> countQuery = queryFactory
                .select(accountBook)
                .from(accountBook)
                .innerJoin(accountBook.user, user)
                .leftJoin(accountBook.accountBookDetail, accountBookDetail)
                .where(
                        user.id.eq(userId),
                        accountBook.deleted.eq(0)
                );

        // limit보다 전체 count가 작으면(=마지막 페이지) count query 호출x 자동화
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }
}
