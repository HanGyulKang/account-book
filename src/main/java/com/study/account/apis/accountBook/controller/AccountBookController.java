package com.study.account.apis.accountBook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/apis/v1/account-book")
public class AccountBookController {

    @GetMapping("/list")
    public String findAccountBookListByUuid() {
        return "list";
    }
}
