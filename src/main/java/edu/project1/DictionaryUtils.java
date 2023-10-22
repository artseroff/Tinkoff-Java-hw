package edu.project1;

import java.util.List;

public class DictionaryUtils {
    private final static List<String> WORDS = List.of("причина", "инквизиция", "ворона", "фуксия", "", null);

    private DictionaryUtils() {
    }

    public static String randomWord() {
        int wordsSize = WORDS.size();
        int randomWordIndex = (int) (Math.random() * wordsSize);
        return WORDS.get(randomWordIndex);
    }

}
