package edu.hw8.task3;

import java.util.HashMap;
import java.util.Map;

public class SimplePasswordHacker extends AbstractPasswordHacker {

    public SimplePasswordHacker(Map<String, String> encryptedPasswords) {
        this.encryptedPasswords = new HashMap<>(encryptedPasswords);
        this.hackedPasswords = new HashMap<>();
    }

    @Override
    public Map<String, String> hack() {
        PasswordGenerator passwordGenerator = new PasswordGenerator();
        while (!encryptedPasswords.isEmpty()) {
            tryFindEncryptedPassword(passwordGenerator.next());
        }
        return hackedPasswords;
    }
}
