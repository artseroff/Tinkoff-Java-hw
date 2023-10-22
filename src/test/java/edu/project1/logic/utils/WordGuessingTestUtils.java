package edu.project1.logic.utils;

public class WordGuessingTestUtils {

    /*@SuppressWarnings("MagicNumber")
    public static Stream<Arguments> parametersWords() {
        return Stream.of(
            Arguments.of("слово"),
            Arguments.of("два"),
            Arguments.of("водоснабжение"),
            Arguments.of("абстрактный"),
            Arguments.of("навигация")
        );
    }*/

    public static char getLetterNotInWord(String word) {
        char letter = 'a';
        while (word.contains(String.valueOf(letter))) {
            letter++;
        }
        return letter;
        //return letter++;
    }
}
