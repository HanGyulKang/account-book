package com.study.account.apis.accountBookDetail.controller;

import com.study.account.apis.accountBook.dto.AccountBookListDto;
import com.study.account.apis.accountBook.service.AccountBookService;
import com.study.account.apis.accountBookDetail.dto.AccountBookDetailResponseDto;
import com.study.account.apis.accountBookDetail.dto.AccountBookDetailSaveDto;
import com.study.account.apis.accountBookDetail.service.AccountBookDetailService;
import com.study.account.common.enums.PaymentTypes;
import com.study.account.common.enums.UserRole;
import com.study.account.entity.AccountBook;
import com.study.account.entity.AccountBookDetail;
import com.study.account.entity.User;
import lombok.extern.slf4j.Slf4j;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
@Slf4j
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
    private Long accountBookDetailId;

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

        // 테스트 가계부 상세 등록
        BigInteger amount11 = new BigInteger(String.valueOf(111111));
        BigInteger amount12 = new BigInteger(String.valueOf(111111));
        BigInteger amount13 = new BigInteger(String.valueOf(111111));
        String storeName1 = "test store 1";
        String storeName2 = "test store 2";
        String storeName3 = "test store 3";

        AccountBookDetail accountBookDetail1 =
                AccountBookDetail.builder().accountBook(accountBook1).amount(amount11).storeName(storeName1)
                        .paymentTypes(PaymentTypes.CASH).paymentDate(LocalDateTime.now()).build();
        AccountBookDetail accountBookDetail2 =
                AccountBookDetail.builder().accountBook(accountBook1).amount(amount12).storeName(storeName2)
                        .paymentTypes(PaymentTypes.CASH).paymentDate(LocalDateTime.now()).build();
        AccountBookDetail accountBookDetail3 =
                AccountBookDetail.builder().accountBook(accountBook1).amount(amount13).storeName(storeName3)
                        .paymentTypes(PaymentTypes.CASH).paymentDate(LocalDateTime.now()).build();

        em.persist(accountBookDetail1);
        em.persist(accountBookDetail2);
        em.persist(accountBookDetail3);

        this.accountBookDetailId = accountBookDetail1.getId();
    }

    @Test
    @DisplayName("[POST] /apis/v1/detail/account-book : 가계부 상세내용 저장")
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

    @Test
    @DisplayName("[DELETE] /apis/v1/detail/account-book : 가계부 상세내용 삭제")
    void deleteAccountBookDetailByAccountBookDetailId() {
        AccountBookDetail afterAccountBookDetail = em.find(AccountBookDetail.class, this.accountBookDetailId);
        log.info("\naccountBookDetail : {}\n", afterAccountBookDetail.toString());
        accountBookDetailService.deleteAccountBookDetailByAccountBookDetailId(this.userId, this.accountBookDetailId);

        AccountBookDetail beforeAccountBookDetail = em.find(AccountBookDetail.class, this.accountBookDetailId);
        assertThat(beforeAccountBookDetail).isNull();
    }
}