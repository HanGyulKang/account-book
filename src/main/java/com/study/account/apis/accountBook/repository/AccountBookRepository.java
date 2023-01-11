package com.study.account.apis.accountBook.repository;

import com.study.account.entity.AccountBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long>
        , AccountBookRepositorySupport
        , QuerydslPredicateExecutor {


}
