package com.study.account.common.util;

import com.study.account.common.auth.PrincipalDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getUsername() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getName() == null) {
            throw new RuntimeException("No authentication");
        }

        return authentication.getName();
    }

    public static Long getUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("No authentication");
        }

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        return principalDetails.getUserId();
    }
}
