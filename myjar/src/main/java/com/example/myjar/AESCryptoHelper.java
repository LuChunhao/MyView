package com.example.myjar;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESCryptoHelper {
    /**
     * @param plainText
     * @param key       密码必须是16位，否则会报错哈
     * @return
     * @throws Exception
     */
    public static String encrypt(String plainText, String key) throws Exception {
        if (plainText == null || key == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = cipher.doFinal(plainText.getBytes("utf-8"));
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * @param encryptText
     * @param key         密码必须是16位，否则会报错哈
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptText, String key) throws Exception {
        if (encryptText == null || key == null) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES"));
        byte[] bytes = Base64.decode(encryptText, Base64.DEFAULT);
        bytes = cipher.doFinal(bytes);
        return new String(bytes, "utf-8");
    }
}