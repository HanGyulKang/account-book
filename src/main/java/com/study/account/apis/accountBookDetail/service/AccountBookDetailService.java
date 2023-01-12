package com.study.account.apis.accountBookDetail.service;

import com.study.account.apis.accountBookDetail.dto.AccountBookDetailResponseDto;
import com.study.account.apis.accountBookDetail.dto.AccountBookDetailSaveDto;

public interface AccountBookDetailService {

    AccountBookDetailResponseDto createAccountBookDetail(AccountBookDetailSaveDto params, Long userId);

    AccountBookDetailResponseDto deleteAccountBookDetailByAccountBookDetailId(Long userId, Long accountBookDetailId);
}
