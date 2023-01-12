package com.study.account.apis.accountBookDetail.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/apis/v1/detail")
public class AccountBookDetailController {

    @PostMapping("/account-book")
    @Description("가계부 상세 내용 입력")
    public String createAccountBookDetail() {
        // 가계부 상세 내역 생성
        return "createAccountBookDetail\n";
    }

    @GetMapping("/account-book")
    @Description("가계부 상세 내용 조회")
    public String findAccountBookByUuidAndAccountBookId() {
        // 가계부 상세 조회
        return "findAccountBookByUuidAndAccountBookId\n";
    }

    @DeleteMapping("/account-book")
    @Description("가계부 상세 내용 삭제")
    public String deleteAccountBookDetailByAccountBookDetailId() {
        return "deleteAccountBookDetailByAccountBookDetailId";
    }
}
