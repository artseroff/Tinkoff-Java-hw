package edu.project1.logic.utils;

public class WordGuessingTestUtils {

    public static char getLetterNotInWord(String word) {
        char letter = 'a';
        while (word.contains(String.valueOf(letter))) {
            letter++;
        }
        return letter;
    }
}
