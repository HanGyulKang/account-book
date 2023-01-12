package com.study.account.apis.accountBook.repository;

import com.study.account.apis.accountBook.dto.AccountBookDeleteDto;
import com.study.account.apis.accountBook.dto.AccountBookListDto;
import com.study.account.apis.accountBook.dto.AccountBookModifyDto;
import com.study.account.apis.accountBook.dto.AccountBookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountBookRepositorySupport {
    Page<AccountBookListDto> findAccountBookByUserId(Pageable pageable, Long userId, Integer deleted);

    AccountBookResponseDto modifyAccountBook(AccountBookModifyDto params, Long userId);

    AccountBookResponseDto modifyAccountBookStatus(AccountBookDeleteDto params, Long userId);
}
