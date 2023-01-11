package com.study.account.apis.user.service.impl;

import com.study.account.apis.user.dto.UserResponseDto;
import com.study.account.apis.user.dto.UserDto;
import com.study.account.apis.user.service.UserService;
import com.study.account.apis.user.util.ResponseUtil;
import com.study.account.common.enums.UserEnum;
import com.study.account.common.enums.UserRole;
import com.study.account.common.jwt.JwtProperties;
import com.study.account.entity.User;
import com.study.account.apis.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EntityManager em;
    private static ResponseUtil responseUtil = new ResponseUtil();

    @Override
    @Transactional
    public UserResponseDto signupWithEmailAndPassword(UserDto params) {
        User user = userRepository.findByUsername(params.getEmail());
        if(user != null) {
            return responseUtil.response(UserEnum.DUPLICATE_EMAIL.getCode(), UserEnum.DUPLICATE_EMAIL.getMessage());
        }

        user = User.builder()
                .username(params.getEmail())
                .password(bCryptPasswordEncoder.encode(params.getPassword()))
                .roles(UserRole.ROLE_USER)
                .build();

        User checkSave = userRepository.save(user);

        if(checkSave != null) {
            return responseUtil.response(UserEnum.SUCCESS_SIGNUP.getCode(), UserEnum.SUCCESS_SIGNUP.getMessage());
        } else {
            return responseUtil.response(UserEnum.FAILED_SIGNUP.getCode(), UserEnum.FAILED_SIGNUP.getMessage());
        }
    }

    @Override
    @Transactional
    public UserResponseDto logout(Long userId) {
        User user = em.find(User.class, userId);
        user.saveUserToken(JwtProperties.EMPTY_STRING);
        return responseUtil.response(UserEnum.SUCCESS_LOGOUT.getCode(), UserEnum.SUCCESS_LOGOUT.getMessage());
    }
}
