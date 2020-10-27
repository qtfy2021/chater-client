package com.example.until;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public class MD5CodeCeator {
    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    public static String md5(String input) throws NoSuchAlgorithmException {
        byte[] bytes = MessageDigest.getInstance("MD5").digest(input.getBytes());

        return printHexBinary(bytes);
    }

    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);

        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }

        return r.toString();
    }

    public static String randomUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
