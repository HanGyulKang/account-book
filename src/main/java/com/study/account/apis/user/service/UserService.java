package com.study.account.apis.user.service;

import com.study.account.apis.user.dto.UserDto;

public interface UserService {
    UserDto.Out signupWithEmailAndPassword(UserDto.In params);

    UserDto.Out logout(Long userId);
}
