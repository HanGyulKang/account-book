package com.study.account.apis.accountBook.controller;

import com.study.account.apis.accountBook.dto.AccountBookDto;
import com.study.account.apis.accountBook.dto.AccountBookListDto;
import com.study.account.apis.accountBook.dto.AccountBookResponseDto;
import com.study.account.apis.accountBook.service.AccountBookService;
import com.study.account.common.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/apis/v1")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @GetMapping("/account-book")
    public ResponseEntity<Page<AccountBookListDto>> findAccountBookByUuid(Pageable pageable) {
        Page<AccountBookListDto> accountBookList = accountBookService.findAccountBookByUuid(pageable);

        if(!accountBookList.isEmpty()) {
            return new ResponseEntity<>(accountBookList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/account-book")
    public ResponseEntity<AccountBookResponseDto> createAccountBook(@RequestBody AccountBookDto params) {
        AccountBookResponseDto response = accountBookService.createAccountBook(params, SecurityUtil.getUserId());

        if(response != null && response.getResultCode().equals(HttpStatus.OK.value())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/account-book")
    public String modifyAccountBook() {
        // 가계부 내용 수정
        return "modifyAccountBook\n";
    }

    @PostMapping("/account-book/detail")
    public String createAccountBookDetail() {
        // 가계부 상세 내역 생성
        return "createAccountBookDetail\n";
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
