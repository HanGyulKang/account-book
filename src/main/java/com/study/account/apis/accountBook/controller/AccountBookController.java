package com.study.account.apis.accountBook.controller;

import com.study.account.apis.accountBook.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/apis/v1")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @GetMapping("/account-book")
    public String findAccountBookByUuid() {
        // 작성한 가계부 내용 조회
        return "findAccountBook\n";
    }

    @PostMapping("/account-book")
    public String createAccountBook() {
        // 가계부 내용 생성
        return "createAccountBook\n";
    }

    @PutMapping("/account-book")
    public String modifyAccountBook() {
        // 가계부 내용 수정
        return "modifyAccountBook\n";
    }

    @GetMapping("/account-book/detail")
    public String findAccountBookByUuidAndAccountBookId() {
        // 가계부 상세 조회
        return "findAccountBookByUuidAndAccountBookId\n";
    }

    @GetMapping("/account-book/recycle-bin")
    public String findAccountBookInTheRecycleBin() {
        // 삭제한 가계부 조회
        return "findAccountBookInTheRecycleBin \n";
    }

    @PutMapping("/account-book/recycle-bin")
    public String modifyAccountBookStatus() {
        // 삭제할 때도 얘, 복구할 때도 얘
        return "modifyAccountBookStatus\n";
    }
}
