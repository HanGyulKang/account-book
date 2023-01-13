package com.study.account.apis.user.controller;

import com.study.account.apis.user.dto.UserResponseDto;
import com.study.account.apis.user.dto.UserDto;
import com.study.account.apis.user.service.UserService;
import com.study.account.common.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/apis/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Description("회원 가입")
    public ResponseEntity<UserResponseDto> signupWithEmailAndPassword(
            @RequestBody UserDto params) {

        UserResponseDto response = userService.signupWithEmailAndPassword(params);
        if(response != null && response.getResultCode().equals(HttpStatus.OK.value())) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/logout")
    @Description("로그 아웃")
    public ResponseEntity<UserResponseDto> logout() {
        UserResponseDto response = userService.logout(SecurityUtil.getUserId());
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
