package org.example.MiniSocialNetwork.util;

import lombok.SneakyThrows;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    private static MessageDigest md;

    static {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }


    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    @SneakyThrows
    public static String toHash(String text){
        byte[] bytesOfMessage = text.getBytes("UTF-8");
        byte[] theMD5digest = md.digest(bytesOfMessage);
        return bytesToHex(theMD5digest);
    }

    public static boolean match(String text, String textHash){
        return toHash(text).equals(textHash);
    }

}