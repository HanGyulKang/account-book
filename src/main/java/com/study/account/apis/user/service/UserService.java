package com.study.account.apis.user.service;

import com.study.account.apis.user.dto.UserResponseDto;
import com.study.account.apis.user.dto.UserDto;

public interface UserService {
    UserResponseDto signupWithEmailAndPassword(UserDto params);

    UserResponseDto logout(Long userId);
}
