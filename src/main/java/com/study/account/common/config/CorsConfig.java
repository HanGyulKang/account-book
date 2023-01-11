package com.study.account.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*"); // 모든 ip에 응답 허용
        corsConfiguration.addAllowedHeader("*"); // 모든 header에 응답 허용
        corsConfiguration.addAllowedMethod("*"); // 모든 http method에 응답 허용

        // /apis/이하 모두 적용
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/apis/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
