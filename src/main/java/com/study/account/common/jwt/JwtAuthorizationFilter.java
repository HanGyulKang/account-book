package com.study.account.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.study.account.common.auth.PrincipalDetails;
import com.study.account.common.util.SecurityUtil;
import com.study.account.entity.User;
import com.study.account.apis.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String requestURI = req.getRequestURI();

        if("/apis/v1/user/signup".equals(requestURI) || "/healthCheck".equals(requestURI)) {
            chain.doFilter(req, res);
        } else {
            // 인증 또는 권한이 필요한 요청 검증 필터(=BasicAuthenticationFilter)
            String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            log.info("JWT Authorization filter : " + jwtToken);

            if(!StringUtils.hasText(jwtToken) || !jwtToken.startsWith(JwtProperties.BEARER)) {
                chain.doFilter(request, response);
            }

            jwtToken = jwtToken.replace(JwtProperties.BEARER, "");

            Map<String, Claim> claims;
            String username = "";
            try {
                claims = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaims();
                username = claims.get(JwtProperties.USER_NAME).asString();
            } catch (Exception e) {
                log.error(e.getMessage());
            }


            if(StringUtils.hasText(username)) {
                // 정상적으로 서명 된 것을 검증
                log.info("verify user toekn : {}", username);
                User user = userRepository.findByUsername(username);

                // 로그인할 때 디비에 저장한 토큰과 비교
                if(StringUtils.hasText(user.getToken()) && jwtToken.equals(user.getToken())) {
                    PrincipalDetails principalDetails = new PrincipalDetails(user);
                    // Jwt Token 서명이 정상적으로 되었기 때문에 임의 인증 객체 생성(로그인을 위함)
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            principalDetails
                            , null
                            , principalDetails.getAuthorities());

                    // security session에 강제로 인증 정보 저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

            chain.doFilter(req, res);
        }
    }
}
