package com.study.account.apis.accountBook.controller;

import com.study.account.apis.accountBook.dto.*;
import com.study.account.apis.accountBook.service.AccountBookService;
import com.study.account.common.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/apis/v1/main")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @GetMapping("/account-book")
    @Description("가계부 전체 조회")
    public ResponseEntity<Page<AccountBookListDto>> findAccountBookByUserId(Pageable pageable) {
        Long userId = SecurityUtil.getUserId();
        Page<AccountBookListDto> accountBookList = accountBookService.findAccountBookByUserId(pageable, userId);

        if(!accountBookList.isEmpty()) {
            return new ResponseEntity<>(accountBookList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/account-book")
    @Description("가계부 입력")
    public ResponseEntity<AccountBookResponseDto> createAccountBook(
            @RequestBody AccountBookSaveDto params) {
        AccountBookResponseDto response = accountBookService.createAccountBook(params, SecurityUtil.getUserId());

        if(response != null && response.getResultCode().equals(HttpStatus.OK.value())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/account-book")
    @Description("가계부 내용 수정(금액, 메모)")
    public ResponseEntity<AccountBookResponseDto> modifyAccountBook(
            @RequestBody AccountBookModifyDto params) {
        AccountBookResponseDto response = accountBookService.modifyAccountBook(params, SecurityUtil.getUserId());

        if(response != null && response.getResultCode().equals(HttpStatus.OK.value())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/account-book/recycle-bin")
    public ResponseEntity<Page<AccountBookListDto>> findAccountBookInTheRecycleBin(Pageable pageable) {
        // 삭제한 가계부 조회
        Long userId = SecurityUtil.getUserId();
        Page<AccountBookListDto> accountBookList = accountBookService.findDeletedAccountBookByUserId(pageable, userId);

        if(!accountBookList.isEmpty()) {
            return new ResponseEntity<>(accountBookList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/account-book/recycle-bin")
    public ResponseEntity<AccountBookResponseDto> modifyAccountBookStatus(
            @RequestBody AccountBookDeleteDto params) {

        Long userId = SecurityUtil.getUserId();
        AccountBookResponseDto response = accountBookService.modifyAccountBookStatus(params, userId);

        if(response != null && response.getResultCode().equals(HttpStatus.OK.value())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }
}
