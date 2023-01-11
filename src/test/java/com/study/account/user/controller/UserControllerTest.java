package com.study.account.user.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.study.account.apis.user.dto.UserResponseDto;
import com.study.account.apis.user.dto.UserDto;
import com.study.account.apis.user.repository.UserRepository;
import com.study.account.apis.user.service.UserService;
import com.study.account.common.jwt.JwtProperties;
import com.study.account.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    EntityManager em;

    private static String email = "test@gmail.com";
    private static String password = "study123";

    @Test
    @Rollback
    @DisplayName("/apis/v1/signup : 회원가입")
    void signupWithEmailAndPassword() {
        // 테스트 데이터 준비
        UserDto params = UserDto
                .builder()
                .email(email)
                .password(password)
                .build();

        // 회원가입 테스트 결과
        UserResponseDto response = userService.signupWithEmailAndPassword(params);
        assertThat(HttpStatus.OK.value()).isEqualTo(response.getResultCode());
    }

    @Test
    @Rollback
    @DisplayName("/apis/v1/user/logout : 로그아웃")
    void logout() {
        UserDto params = UserDto
                .builder()
                .email(email)
                .password(password)
                .build();
        // 테스트 데이터 준비
        userService.signupWithEmailAndPassword(params);
        User user = userRepository.findByUsername(email);

        String jwtToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim(JwtProperties.ID, user.getId())
                .withClaim(JwtProperties.USER_NAME, user.getUsername())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        // 테스트 토큰 저장
        user.saveUserToken(jwtToken);
        userRepository.save(user);

        // 로그아웃 테스트 결과
        UserResponseDto logout = userService.logout(user.getId());
        assertThat(logout.getResultCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}