package com.study.account;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class AccountApplication {

    @Value("${env.value}")
    private String env;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "health : " + env + "\n";
    }

    @PostMapping("/login")
    @Description("로그인")
    public void login() {
        // Spring security의 login을 사용하기 때문에 실제로 사용 되지는 않음
        // 형식상 만들어 둠
    }

    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
