package com.example.bookmall.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtil {
    private static final int SALT_LENGTH = 16;

    private PasswordUtil() {
    }

    public static String generateSalt() {
        byte[] salt = new byte[SALT_LENGTH];
        new SecureRandom().nextBytes(salt);
        return toHex(salt);
    }

    public static String hashWithSalt(String password, String salt) {
        return md5Hex(password + salt);
    }

    private static String md5Hex(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            return toHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 algorithm not available", e);
        }
    }

    private static String toHex(byte[] data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            String hex = Integer.toHexString(b & 0xff);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
