package com.study.account.apis.accountBook.service;

import com.study.account.apis.accountBook.dto.AccountBookModifyDto;
import com.study.account.apis.accountBook.dto.AccountBookSaveDto;
import com.study.account.apis.accountBook.dto.AccountBookListDto;
import com.study.account.apis.accountBook.dto.AccountBookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountBookService {
    AccountBookResponseDto createAccountBook(AccountBookSaveDto params, Long userId);
    Page<AccountBookListDto> findAccountBookByUuid(Pageable pageable, Long userId);
    AccountBookResponseDto modifyAccountBook(AccountBookModifyDto params, Long userId);
}
