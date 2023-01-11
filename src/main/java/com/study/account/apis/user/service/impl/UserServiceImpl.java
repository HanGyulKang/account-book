package com.study.account.apis.user.service.impl;

import com.study.account.apis.user.dto.UserDto;
import com.study.account.apis.user.service.UserService;
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

        User checkSave = userRepository.save(user);

        if(checkSave != null) {
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

    @Override
    @Transactional
    public UserDto.Out logout(Long userId) {
        User user = em.find(User.class, userId);
        user.saveUserToken(JwtProperties.EMPTY_STRING);

        return UserDto.Out
                .builder()
                .resultCode(0)
                .resultMessage("로그아웃 완료")
                .build();
    }
}
