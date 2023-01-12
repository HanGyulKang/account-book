package com.study.account.apis.accountBookDetail.controller;

import com.study.account.apis.accountBookDetail.dto.AccountBookDetailResponseDto;
import com.study.account.apis.accountBookDetail.dto.AccountBookDetailSaveDto;
import com.study.account.apis.accountBookDetail.service.AccountBookDetailService;
import com.study.account.common.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/apis/v1/detail")
public class AccountBookDetailController {

    private final AccountBookDetailService accountBookDetailService;

    @PostMapping("/account-book")
    @Description("가계부 상세 내용 입력")
    public ResponseEntity<AccountBookDetailResponseDto> createAccountBookDetail(
            @RequestBody AccountBookDetailSaveDto params) {
        Long userId = SecurityUtil.getUserId();
        AccountBookDetailResponseDto response = accountBookDetailService.createAccountBookDetail(params, userId);

        if(response != null && response.getResultCode().equals(HttpStatus.OK.value())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
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
