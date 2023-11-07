package edu.hw3.Task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Task1Test {

    private static final String NON_LATIN_SYMBOLS = "ПоКа_012345!-+?";

    @ParameterizedTest
    @CsvSource(value = {
        NON_LATIN_SYMBOLS+", "+NON_LATIN_SYMBOLS,
        "Hello world!, Svool dliow!"})
    public void atbashParamTest(String input, String expected) {

        String actual = new Task1().atbash(input);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void atbashAllDictionaryTest() {
        // test all latin letters plus some non latin sequence from constant
        String expected;
        StringBuilder sbLetters = new StringBuilder();
        for (char i = 'A'; i <= 'Z'; i++) {
            sbLetters.append(i);
        }
        expected = sbLetters.toString();
        sbLetters.reverse();
        StringBuilder sbSmallLetters = new StringBuilder();
        for (char i = 'a'; i <= 'z'; i++) {
            sbSmallLetters.append(i);
        }
        expected += sbSmallLetters.toString();
        sbSmallLetters.reverse();

        sbLetters.append(sbSmallLetters);

        String input = sbLetters + NON_LATIN_SYMBOLS;

        expected += NON_LATIN_SYMBOLS;
        // expected variable is reversed all latin letters plus non latin sequence
        String actual = new Task1().atbash(input);
        Assertions.assertEquals(expected, actual);
    }
}
