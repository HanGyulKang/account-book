package com.study.account.common.util;

import java.util.UUID;
public class IdGenUtil {

    public static String generate() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "").toUpperCase();
    }

}
