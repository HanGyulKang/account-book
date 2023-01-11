package com.study.account.common.config;

import com.study.account.common.filter.CommonFilter;
import com.study.account.common.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Filter 우선 순위 : BasicAuthenticationFilter > SpringSecurityFilter
        http.addFilterBefore(new CommonFilter(), BasicAuthenticationFilter.class);

        http.csrf().disable();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 x
                .and()
                .addFilter(corsFilter) // cors 정책에서 벗어남
                .formLogin().disable()
                .httpBasic().disable()
                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .authorizeRequests()
                .antMatchers("/apis/v1/user/signup")
                    .permitAll()
                .antMatchers("/apis/v1/user/**")
                    .access("hasRole('USER')")
                .antMatchers("/apis/v1/account/**")
                    .access("hasRole('USER')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginProcessingUrl("/login");
    }
}
