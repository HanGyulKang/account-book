package com.study.account.user.service;

import com.study.account.user.dto.UserDto;

public interface UserService {
    UserDto.Out signupWithEmailAndPassword(UserDto.In params);
}
