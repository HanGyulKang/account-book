package com.study.account.apis.accountBookDetail.controller;

import com.study.account.apis.accountBook.dto.AccountBookListDto;
import com.study.account.apis.accountBook.service.AccountBookService;
import com.study.account.apis.accountBookDetail.dto.AccountBookDetailResponseDto;
import com.study.account.apis.accountBookDetail.dto.AccountBookDetailSaveDto;
import com.study.account.apis.accountBookDetail.service.AccountBookDetailService;
import com.study.account.common.enums.UserRole;
import com.study.account.common.util.SecurityUtil;
import com.study.account.entity.AccountBook;
import com.study.account.entity.AccountBookDetail;
import com.study.account.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class AccountBookDetailControllerTest {

    @Autowired
    AccountBookService accountBookService;
    @Autowired
    AccountBookDetailService accountBookDetailService;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EntityManager em;

    private static String email = "test@gmail.com";
    private static String password = "study123";
    private Long userId;

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
    }

    @Test
    void createAccountBookDetail() {
        // 테스트 데이터 준비
        PageRequest pageRequest = PageRequest.of(0, 1);
        Page<AccountBookListDto> accountBookList = accountBookService.findAccountBookByUserId(pageRequest, this.userId);

        Long accountBookId = accountBookList.getContent().get(0).getAccountBookId();
        BigInteger amount = new BigInteger(String.valueOf(111111));
        String storeName = "test store 1";
        String paymentTypes = "CASH";
        Long paymentDate = 1642010220L;

        AccountBookDetailSaveDto params = new AccountBookDetailSaveDto(
                accountBookId,
                amount,
                storeName,
                paymentTypes,
                paymentDate
        );

        // 테스트
        AccountBookDetailResponseDto response
                = accountBookDetailService.createAccountBookDetail(params, this.userId);

        // 결과
        AccountBookDetail accountBookDetail = em.find(AccountBookDetail.class, response.getId());
        assertThat(accountBookDetail.getAccountBook().getId()).isEqualTo(accountBookId);
        assertThat(accountBookDetail.getAccountBook().getUser().getId()).isEqualTo(this.userId);
        assertThat(accountBookDetail.getAmount()).isEqualTo(amount);
        assertThat(accountBookDetail.getStoreName()).isEqualTo(storeName);
    }
}