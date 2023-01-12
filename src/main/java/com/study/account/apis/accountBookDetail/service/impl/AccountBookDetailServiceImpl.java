package com.study.account.apis.accountBookDetail.service.impl;

import com.study.account.apis.accountBookDetail.repository.AccountBookDetailRepository;
import com.study.account.apis.accountBookDetail.service.AccountBookDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountBookDetailServiceImpl implements AccountBookDetailService {

    private final AccountBookDetailRepository accountBookDetailRepository;
}
