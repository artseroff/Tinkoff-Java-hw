package edu.project1.logic;

import edu.project1.exceptions.InvalidWordException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class GuessedWordTest {
    @ParameterizedTest
    @ValueSource(strings = {"", "а1"})
    public void invalidGuessedWord(String input) {
        Assertions.assertThrows(InvalidWordException.class, () -> new GuessedWord(input));
    }

    @Test
    public void nullGuessedWord() {
        Assertions.assertThrows(InvalidWordException.class, () -> new GuessedWord(null));
    }

    @ParameterizedTest
    @ValueSource(strings = {"причина", "инквизиция", "ворона", "фуксия", "едва"})
    public void lengthTest(String input) {
        Assertions.assertEquals(input.trim().length(), new GuessedWord(input).getLength());
    }

    @ParameterizedTest
    @MethodSource("provideWordsAndChars")
    public void guessChar(String input, char ch, boolean expected) {
        GuessedWord word = new GuessedWord(input);
        Assertions.assertEquals(expected, word.guessChar(ch));
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] provideWordsAndChars() {
        return new Arguments[] {
            Arguments.of("причина", 'п', true),
            Arguments.of("причина", 'ы', false),
            Arguments.of("опять", 'о', true),
            Arguments.of("опять", 'е', false)
        };
    }

    @ParameterizedTest
    @ValueSource(strings = {"причина", "опять", "едва"})
    public void encryptedView(String input) {
        char ch = input.charAt(0);
        String expectedView = ch + "*".repeat(input.length() - 1);
        GuessedWord word = new GuessedWord(input);
        word.guessChar(ch);
        Assertions.assertEquals(expectedView, word.encryptedView());
    }

}
