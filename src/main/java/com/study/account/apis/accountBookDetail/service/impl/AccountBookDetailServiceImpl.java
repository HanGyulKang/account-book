package com.study.account.apis.accountBookDetail.service.impl;

import com.study.account.apis.accountBook.repository.AccountBookRepository;
import com.study.account.apis.accountBookDetail.dto.AccountBookDetailResponseDto;
import com.study.account.apis.accountBookDetail.dto.AccountBookDetailSaveDto;
import com.study.account.apis.accountBookDetail.repository.AccountBookDetailRepository;
import com.study.account.apis.accountBookDetail.service.AccountBookDetailService;
import com.study.account.apis.accountBookDetail.util.ResponseUtil;
import com.study.account.common.enums.PaymentTypes;
import com.study.account.entity.AccountBook;
import com.study.account.entity.AccountBookDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountBookDetailServiceImpl implements AccountBookDetailService {

    private final AccountBookDetailRepository accountBookDetailRepository;
    private final AccountBookRepository accountBookRepository;

    private static ResponseUtil responseUtil = new ResponseUtil();

    @Override
    @Transactional
    public AccountBookDetailResponseDto createAccountBookDetail(AccountBookDetailSaveDto params, Long userId) {
        Optional<AccountBook> accountBook = accountBookRepository.findById(params.getAccountBookId());

        PaymentTypes paymentTypes;
        if(params.getPaymentTypes().equals(PaymentTypes.CASH.name())) {
            paymentTypes = PaymentTypes.CASH;
        } else if(params.getPaymentTypes().equals(PaymentTypes.CREDIT_CARD.name())) {
            paymentTypes = PaymentTypes.CREDIT_CARD;
        } else {
            paymentTypes = PaymentTypes.UNKNOWN;
        }

        AccountBookDetail accountBookDetail = AccountBookDetail.builder()
                .accountBook(accountBook.get())
                .amount(params.getAmount())
                .paymentDate(params.getPaymentDate())
                .paymentTypes(paymentTypes)
                .storeName(params.getStoreName())
                .build();

        AccountBookDetail saveAccountBookDetail = accountBookDetailRepository.save(accountBookDetail);

        if(saveAccountBookDetail != null) {
            return responseUtil.response(HttpStatus.OK.value(), HttpStatus.OK.toString(), saveAccountBookDetail.getId());
        } else {
            return responseUtil.response(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString());
        }
    }
}
