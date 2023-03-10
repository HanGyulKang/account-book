package com.study.account.apis.accountBook.controller;

import com.study.account.apis.accountBook.dto.*;
import com.study.account.apis.accountBook.service.AccountBookService;
import com.study.account.common.enums.UserRole;
import com.study.account.entity.AccountBook;
import com.study.account.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class AccountBookControllerTest {

    @Autowired
    AccountBookService accountBookService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EntityManager em;

    private static String email = "test@gmail.com";
    private static String password = "study123";
    private Long userId;
    private Long accountBookId;

    @BeforeEach
    void setTestUser() {
        // 테스트 유저 등록
        User user = User.builder()
                .username(email)
                .password(bCryptPasswordEncoder.encode(password))
                .roles(UserRole.ROLE_USER)
                .build();

        em.persist(user);
        this.userId = user.getId();

        // 테스트 가계부 등록
        BigInteger amount1 = new BigInteger(String.valueOf(1001));
        BigInteger amount2 = new BigInteger(String.valueOf(1002));
        BigInteger amount3 = new BigInteger(String.valueOf(1003));
        AccountBook accountBook1 =
                AccountBook.builder().amount(amount1).memo("book1")
                        .user(user).deleted(0).recordDate(LocalDateTime.now()).build();
        AccountBook accountBook2 =
                AccountBook.builder().amount(amount2).memo("book2")
                        .user(user).deleted(0).recordDate(LocalDateTime.now()).build();
        AccountBook accountBook3 =
                AccountBook.builder().amount(amount3).memo("book3")
                        .user(user).deleted(0).recordDate(LocalDateTime.now()).build();
        em.persist(accountBook1);
        em.persist(accountBook2);
        em.persist(accountBook3);

        this.accountBookId = accountBook2.getId();
    }

    @Test
    @DisplayName("[POST] /apis/v1/account-book : 가계부 내용 생성")
    void createAccountBook() {
        // 테스트 데이터 준비
        User user = em.find(User.class, this.userId);
        BigInteger amount = new BigInteger(String.valueOf(1049502192));
        String memo = "testMemo";

        // 테스트 데이터 입력
        AccountBookSaveDto params = new AccountBookSaveDto();
        params.setAmount(amount);
        params.setMemo(memo);

        AccountBookResponseDto accountBook = accountBookService.createAccountBook(params, user.getId());
        AccountBook dbAccountBook = em.find(AccountBook.class, accountBook.getId());

        // 결과
        assertThat(dbAccountBook.getId()).isEqualTo(accountBook.getId());
        assertThat(dbAccountBook.getMemo()).isEqualTo(memo);
        assertThat(dbAccountBook.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("[GET] /apis/v1/account-book : 가계부 내용 조회")
    void findAccountBookByUserId() {
        // 페이지 준비
        PageRequest pageRequest = PageRequest.of(0, 3);

        // 조회 테스트
        Page<AccountBookListDto> accountBookList = accountBookService.findAccountBookByUserId(pageRequest, this.userId);
        assertThat(accountBookList.getSize()).isEqualTo(3);
        assertThat(accountBookList.getContent())
                .extracting("memo")
                .contains("book1", "book2", "book3");
        assertThat(accountBookList.getContent())
                .extracting("amount")
                .contains(new BigInteger(String.valueOf(1001)),
                        new BigInteger(String.valueOf(1002)),
                        new BigInteger(String.valueOf(1003)));
    }

    @Test
    @DisplayName("[PUT] /apis/v1/account-book : 가계부 내용 수정")
    void modifyAccountBook() {
        // 테스트 데이터 준비
        PageRequest pageRequest = PageRequest.of(0, 1);
        BigInteger amount = new BigInteger(String.valueOf(1111));
        String memo = "book1 modify";

        Page<AccountBookListDto> accountBookList = accountBookService.findAccountBookByUserId(pageRequest, this.userId);
        AccountBookListDto accountBook = accountBookList.getContent().get(0);

        AccountBookModifyDto params = new AccountBookModifyDto();
        params.setAccountBookId(accountBook.getAccountBookId());
        params.setMemo(memo);
        params.setAmount(amount);

        // 수정
        accountBookService.modifyAccountBook(params, this.userId);

        // 결과
        AccountBook result = em.find(AccountBook.class, accountBook.getAccountBookId());
        assertThat(result.getMemo()).isEqualTo(memo);
        assertThat(result.getAmount()).isEqualTo(amount);
    }

    @Test
    @DisplayName("[PUT] /account-book/recycle-bin : 가계부 삭제 또는 원복")
    void modifyAccountBookStatus() {
        // 테스트 데이터 준비
        AccountBook accountBook = em.find(AccountBook.class, this.accountBookId);
        PageRequest pageRequest = PageRequest.of(0, 10);
        int delete = 1;
        int restore = 0;
        Long userId = accountBook.getUser().getId();

        AccountBookDeleteDto params = new AccountBookDeleteDto();
        params.setAccountBookId(accountBook.getId());
        params.setDelete(delete);

        // 삭제
        accountBookService.modifyAccountBookStatus(params, userId);
        // 삭제 목록 조회
        List<AccountBookListDto> deleteAccountBook
                = accountBookService.findDeletedAccountBookByUserId(pageRequest, userId).getContent();
        // 테스트
        assertThat(deleteAccountBook).extracting("accountBookId")
                .contains(accountBook.getId());

        // 복구
        params.setDelete(restore);
        accountBookService.modifyAccountBookStatus(params, userId);
        // 정상 목록 조회
        List<AccountBookListDto> restoreAccountBook
                = accountBookService.findAccountBookByUserId(pageRequest, userId).getContent();
        // 테스트
        assertThat(restoreAccountBook).extracting("accountBookId")
                .contains(accountBook.getId());
    }
}