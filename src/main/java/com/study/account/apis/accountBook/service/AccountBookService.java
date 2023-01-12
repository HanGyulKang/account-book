package com.study.account.apis.accountBook.service;

import com.study.account.apis.accountBook.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountBookService {
    AccountBookResponseDto createAccountBook(AccountBookSaveDto params, Long userId);

    Page<AccountBookListDto> findAccountBookByUserId(Pageable pageable, Long userId);

    AccountBookResponseDto modifyAccountBook(AccountBookModifyDto params, Long userId);

    Page<AccountBookListDto> findDeletedAccountBookByUserId(Pageable pageable, Long userId);

    AccountBookResponseDto modifyAccountBookStatus(AccountBookDeleteDto params, Long userId);
}
