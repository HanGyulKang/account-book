package com.study.account.apis.accountBook.repository.impl;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.account.apis.accountBook.dto.*;
import com.study.account.apis.accountBook.repository.AccountBookRepositorySupport;
import com.study.account.apis.accountBook.util.ResponseUtil;
import com.study.account.entity.AccountBook;
import com.study.account.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.http.HttpStatus;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

import static com.study.account.entity.QAccountBook.accountBook;
import static com.study.account.entity.QAccountBookDetail.accountBookDetail;
import static com.study.account.entity.QUser.user;

public class AccountBookRepositorySupportImpl extends QuerydslRepositorySupport
        implements AccountBookRepositorySupport {

    private final JPAQueryFactory queryFactory;

    private static ResponseUtil responseUtil = new ResponseUtil();

    public AccountBookRepositorySupportImpl(EntityManager em) {
        super(AccountBook.class);
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<AccountBookListDto> findAccountBookByUserId(Pageable pageable, Long userId, Integer deleted) {
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
                        accountBook.deleted.eq(deleted)
                )
                .orderBy(accountBook.id.desc(), accountBookDetail.id.asc())
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

        // limit?????? ?????? count??? ?????????(=????????? ?????????) count query ??????x ?????????
        return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
    }

    @Override
    public AccountBookResponseDto modifyAccountBook(AccountBookModifyDto params, Long userId) {
        AccountBook getAccountBook = getEntityManager().find(AccountBook.class, params.getAccountBookId());

        if(getAccountBook != null && getAccountBook.getUser().getId() == userId) {
            // Dirty checking
            getAccountBook.modifyAmountOrMemoInAccountBook(
                    params.getAmount() == null ? getAccountBook.getAmount() : params.getAmount(),
                    params.getMemo() == null ? getAccountBook.getMemo() : params.getMemo()
            );

            return responseUtil.response(HttpStatus.OK.value(), HttpStatus.OK.toString());
        } else {
            return responseUtil.response(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString());
        }
    }

    @Override
    public AccountBookResponseDto modifyAccountBookStatus(AccountBookDeleteDto params, Long userId) {
        AccountBook getAccountBook = getEntityManager().find(AccountBook.class, params.getAccountBookId());

        if(getAccountBook != null && getAccountBook.getUser().getId() == userId) {
            // Dirty checking
            getAccountBook.modifyDeletedInAccountBook(params.getDelete());
            return responseUtil.response(HttpStatus.OK.value(), HttpStatus.OK.toString());
        } else {
            return responseUtil.response(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString());
        }
    }
}
