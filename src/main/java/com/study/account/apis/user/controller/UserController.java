package com.study.account.apis.user.controller;

import com.study.account.apis.user.dto.UserDto;
import com.study.account.apis.user.service.UserService;
import com.study.account.common.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<UserDto.Out> signupWithEmailAndPassword(
            @RequestBody UserDto.In params) {

        UserDto.Out out = userService.signupWithEmailAndPassword(params);
        if(out != null && out.getResultCode().equals(HttpStatus.OK.value())) {
            return new ResponseEntity<>(out, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(out, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("logout")
    public ResponseEntity<UserDto.Out> logout() {
        UserDto.Out out = userService.logout(SecurityUtil.getUserId());
        return new ResponseEntity<>(out, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/userId")
    public Long getUserId() {
        return SecurityUtil.getUserId();
    }
}
