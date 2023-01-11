package com.study.account.user.service.impl;

import com.study.account.common.enums.UserRole;
import com.study.account.common.util.IdGenUtil;
import com.study.account.common.util.StringUtils;
import com.study.account.entity.User;
import com.study.account.user.dto.UserDto;
import com.study.account.user.repository.UserRepository;
import com.study.account.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public UserDto.Out signupWithEmailAndPassword(UserDto.In params) {
        User user = User
                .builder()
                .username(params.getEmail())
                .password(bCryptPasswordEncoder.encode(params.getPassword()))
                .roles(UserRole.USER)
                .build();

        User save = userRepository.save(user);

        if(save != null) {
            return UserDto.Out
                    .builder()
                    .resultCode(200)
                    .resultMessage("가입 성공")
                    .build();
        } else {
            return UserDto.Out.builder().build();
        }
    }
}
