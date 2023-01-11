package com.study.account.user.controller;

import com.study.account.user.dto.UserDto;
import com.study.account.user.repository.UserRepository;
import com.study.account.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/apis/v1/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto.Out> signupWithEmailAndPassword(
            @RequestBody UserDto.In params) {

        UserDto.Out out = userService.signupWithEmailAndPassword(params);
        if(out != null && out.getResultCode().equals(HttpStatus.OK.value())) {
            return new ResponseEntity<>(out, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(out, HttpStatus.BAD_REQUEST);
        }
    }
}
