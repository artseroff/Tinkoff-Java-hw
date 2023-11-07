package edu.hw3.Task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class Task4Test {
    private static final Task4 TASK_4 = new Task4();

    @ParameterizedTest
    @MethodSource("validNumbers")
    public void convertToRomanValidTest(int n, String expected) {
        String actual = TASK_4.convertToRoman(n);
        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Stream<Arguments> validNumbers() {
        return Stream.of(
            Arguments.of(1, "I"),
            Arguments.of(2, "II"),
            Arguments.of(10, "X"),
            Arguments.of(15, "XV"),
            Arguments.of(19, "XIX"),
            Arguments.of(22, "XXII"),
            Arguments.of(3999, "MMMCMXCIX")
        );
    }

    @ParameterizedTest
    @MethodSource("wrongNumbers")
    public void convertToRomanInvalidArgumentTest(int n) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_4.convertToRoman(n));
    }

    @SuppressWarnings("MagicNumber")
    private static Stream<Arguments> wrongNumbers() {
        return Stream.of(
            Arguments.of(0),
            Arguments.of(-1),
            Arguments.of(-958)
        );
    }
}
