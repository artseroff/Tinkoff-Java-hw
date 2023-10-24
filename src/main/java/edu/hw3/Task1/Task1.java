package edu.hw3.Task1;

public class Task1 {
    private static final char SMALL_A = 'a';
    private static final char SMALL_Z = 'z';

    private static final char HIGH_A = 'A';
    private static final char HIGH_Z = 'Z';

    public String atbash(String input) {
        char[] chars = input.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (isLatinUpperCase(chars[i])) {
                chars[i] = (char) (HIGH_Z - chars[i] + HIGH_A);
            } else if (isLatinLowerCase(chars[i])) {
                chars[i] = (char) (SMALL_Z - chars[i] + SMALL_A);
            }
        }
        return String.valueOf(chars);
    }

    private boolean isLatinUpperCase(char ch) {
        return (ch >= HIGH_A && ch <= HIGH_Z);
    }

    private boolean isLatinLowerCase(char ch) {
        return (ch >= SMALL_A && ch <= SMALL_Z);
    }
}
