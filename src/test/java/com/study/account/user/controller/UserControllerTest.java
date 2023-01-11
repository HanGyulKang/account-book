package com.study.account.user.controller;

import com.study.account.entity.User;
import com.study.account.apis.user.dto.UserDto;
import com.study.account.apis.user.repository.UserRepository;
import com.study.account.apis.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("/apis/v1/signup : 회원가입")
    @Transactional
    @Rollback
    void signupWithEmailAndPassword() {
        String email = "test@gmail.com";
        String password = "study123";

        UserDto.In params = UserDto.In
                .builder()
                .email(email)
                .password(password)
                .build();

        UserDto.Out out = userService.signupWithEmailAndPassword(params);
        assertThat(HttpStatus.OK.value()).isEqualTo(out.getResultCode());
    }
}