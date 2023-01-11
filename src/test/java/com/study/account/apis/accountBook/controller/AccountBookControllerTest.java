package com.study.account.apis.accountBook.controller;

import com.study.account.apis.accountBook.dto.AccountBookDto;
import com.study.account.apis.accountBook.dto.AccountBookResponseDto;
import com.study.account.apis.accountBook.service.AccountBookService;
import com.study.account.common.enums.UserRole;
import com.study.account.entity.AccountBook;
import com.study.account.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    @BeforeEach
    void setTestUser() {
        User user = User.builder()
                .username(email)
                .password(bCryptPasswordEncoder.encode(password))
                .roles(UserRole.ROLE_USER)
                .build();

        em.persist(user);
        this.userId = user.getId();
    }

    @Test
    @DisplayName("[POST] /apis/v1/account-book : 가계부 내용 생성")
    void createAccountBook() {
        // 테스트 데이터 준비
        User user = em.find(User.class, this.userId);
        BigInteger amount = new BigInteger(String.valueOf(1049502192));
        String memo = "testMemo";

        // 테스트 데이터 입력
        AccountBookDto params = new AccountBookDto();
        params.setAmount(amount);
        params.setMemo(memo);

        AccountBookResponseDto accountBook = accountBookService.createAccountBook(params, user.getId());
        AccountBook dbAccountBook = em.find(AccountBook.class, accountBook.getId());

        // 결과
        assertThat(dbAccountBook.getId()).isEqualTo(accountBook.getId());
        assertThat(dbAccountBook.getMemo()).isEqualTo(memo);
        assertThat(dbAccountBook.getAmount()).isEqualTo(amount);
    }
}