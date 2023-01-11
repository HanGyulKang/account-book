package com.study.account.common.util;

public class StringUtils {
    public static byte[] hexStringToByteArray(String challengeId) {

        int length = challengeId.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(challengeId.charAt(i), 16) << 4)
                    + Character.digit(challengeId.charAt(i + 1), 16));
        }
        return data;
    }

    public static String byteArrayToString(byte[] bytes) {
        if(bytes == null || bytes.length == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b & 0xff));
        }

        return sb.toString();
    }
}
