package com.probal.merchantpaymentdemo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@UtilityClass
public class EncryptionUtils {

    private static final String KEY = "cRfUjXnZr4u7x!A%";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final String CHARSET_NAME = "UTF-8";

    public static String encrypt(String payload) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(payload.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static <T> T decryptPayload(String encryptedPayload, Class<T> clazz) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedPayloadBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedPayload));
        String decryptedPayload = new String(decryptedPayloadBytes, CHARSET_NAME);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(decryptedPayload, clazz);
    }
}
