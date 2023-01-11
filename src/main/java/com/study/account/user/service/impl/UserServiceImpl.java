package com.study.account.user.service.impl;

import com.study.account.common.enums.UserRole;
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
        User user = userRepository.findByUsername(params.getEmail());
        if(user != null) {
            return UserDto.Out
                    .builder()
                    .resultCode(400)
                    .resultMessage("이메일 주소 중복")
                    .build();
        }

        user = User
                .builder()
                .username(params.getEmail())
                .password(bCryptPasswordEncoder.encode(params.getPassword()))
                .roles(UserRole.ROLE_USER)
                .build();

        User save = userRepository.save(user);

        if(save != null) {
            return UserDto.Out
                    .builder()
                    .resultCode(200)
                    .resultMessage("가입 성공")
                    .build();
        } else {
            return UserDto.Out
                    .builder()
                    .resultCode(400)
                    .resultMessage("가입 실패")
                    .build();
        }
    }
}
