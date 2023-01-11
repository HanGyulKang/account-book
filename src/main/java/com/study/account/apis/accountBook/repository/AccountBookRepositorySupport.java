package com.study.account.apis.accountBook.repository;

import com.study.account.apis.accountBook.dto.AccountBookListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountBookRepositorySupport {
    Page<AccountBookListDto> findAccountBookByUuid(Pageable pageable);
}
