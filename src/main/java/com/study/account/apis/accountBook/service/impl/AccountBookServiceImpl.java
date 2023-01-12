package com.study.account.apis.accountBook.service.impl;

import com.study.account.apis.accountBook.dto.AccountBookModifyDto;
import com.study.account.apis.accountBook.dto.AccountBookSaveDto;
import com.study.account.apis.accountBook.dto.AccountBookListDto;
import com.study.account.apis.accountBook.dto.AccountBookResponseDto;
import com.study.account.apis.accountBook.repository.AccountBookRepository;
import com.study.account.apis.accountBook.service.AccountBookService;
import com.study.account.apis.accountBook.util.ResponseUtil;
import com.study.account.apis.user.repository.UserRepository;
import com.study.account.entity.AccountBook;
import com.study.account.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountBookServiceImpl implements AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final UserRepository userRepository;

    private static ResponseUtil responseUtil = new ResponseUtil();

    @Override
    @Transactional
    public AccountBookResponseDto createAccountBook(AccountBookSaveDto params, Long userId) {
        User user = userRepository.findById(userId).get();

        AccountBook accountBook = AccountBook
                .builder()
                .amount(params.getAmount())
                .memo(params.getMemo())
                .recordDate(LocalDateTime.now())
                .deleted(0)
                .user(user)
                .build();

        AccountBook saveAccountBook = accountBookRepository.save(accountBook);
        if(saveAccountBook != null) {
            return responseUtil.response(HttpStatus.OK.value(), HttpStatus.OK.toString(), saveAccountBook.getId());
        } else {
            return responseUtil.response(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString());
        }

    }

    @Override
    public Page<AccountBookListDto> findAccountBookByUserId(Pageable pageable, Long userId) {
        return accountBookRepository.findAccountBookByUserId(pageable, userId);
    }

    @Override
    @Transactional
    public AccountBookResponseDto modifyAccountBook(AccountBookModifyDto params, Long userId) {
        return accountBookRepository.modifyAccountBook(params, userId);
    }
}
