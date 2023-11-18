package edu.project1.logic;

import edu.project1.exceptions.InvalidWordException;

public class GuessedWord {
    private static final String ENCRYPTED_CHAR = "*";
    private final char[] chars;
    private final boolean[] isGuessed;
    private final int length;

    public GuessedWord(String word) {
        chars = validateWord(word).trim().toLowerCase().toCharArray();
        length = chars.length;
        isGuessed = new boolean[length];
    }

    private static String validateWord(String word) {
        if (word == null || word.length() <= 2) {
            throw new InvalidWordException("Загадываемое слово имеет некорректную длину");
        }
        if (!word.matches("[а-я]+")) {
            throw new InvalidWordException("Загадываемое слово состоит не только из русских букв");
        }
        return word;
    }

    public String getStringWord() {
        return String.valueOf(chars);
    }

    public int getLength() {
        return length;
    }

    public String encryptedView() {
        String word = "";
        for (int i = 0; i < length; i++) {
            word += isGuessed[i] ? chars[i] : ENCRYPTED_CHAR;
        }
        return word;
    }

    public boolean isWordGuessed() {
        for (int i = 0; i < length; i++) {
            if (!isGuessed[i]) {
                return false;
            }
        }
        return true;
    }

    public boolean guessChar(char ch) {
        boolean isCharGuessed = false;
        for (int i = 0; i < length; i++) {
            if (chars[i] == ch && !isGuessed[i]) {
                isGuessed[i] = true;
                isCharGuessed = true;
            }
        }
        return isCharGuessed;
    }
}
