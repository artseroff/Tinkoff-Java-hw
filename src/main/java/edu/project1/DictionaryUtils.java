package edu.project1;

import java.util.List;

public class DictionaryUtils {
    private final static List<String> WORDS = List.of("причина", "инквизиция", "ворона", "фуксия");

    private DictionaryUtils() {
    }

    public static String randomWord() {
        int wordsSize = WORDS.size();
        int randomWordIndex = (int) (Math.random() * wordsSize);
        String word = WORDS.get(randomWordIndex).trim().toLowerCase();
        return word;
    }

}
