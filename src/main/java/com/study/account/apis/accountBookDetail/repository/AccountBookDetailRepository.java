package com.study.account.apis.accountBookDetail.repository;

import com.study.account.entity.AccountBookDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface AccountBookDetailRepository extends JpaRepository<AccountBookDetail, Long>,
        AccountBookDetailRepositorySupport,
        QuerydslPredicateExecutor {

}
