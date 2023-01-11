package com.study.account.apis.accountBook.service;

import com.study.account.apis.accountBook.dto.AccountBookDto;
import com.study.account.apis.accountBook.dto.AccountBookListDto;
import com.study.account.apis.accountBook.dto.AccountBookResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountBookService {
    AccountBookResponseDto createAccountBook(AccountBookDto params, Long userId);
    Page<AccountBookListDto> findAccountBookByUuid(Pageable pageable);
}
