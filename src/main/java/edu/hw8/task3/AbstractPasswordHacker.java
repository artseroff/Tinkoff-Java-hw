package edu.hw8.task3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public abstract class AbstractPasswordHacker {
    protected Map<String, String> encryptedPasswords;
    protected Map<String, String> hackedPasswords;

    public abstract Map<String, String> hack();

    protected void tryFindEncryptedPassword(String password) {
        String encryptedPassword = encrypt(password);
        if (encryptedPasswords.containsKey(encryptedPassword)) {
            hackedPasswords.put(encryptedPasswords.get(encryptedPassword), password);
            encryptedPasswords.remove(encryptedPassword);
        }
    }

    @SuppressWarnings("MagicNumber")
    private String encrypt(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte elByte : bytes) {
                sb.append(Integer.toString((elByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Ошибка при вычислении хэша MD5");
        }
    }
}
