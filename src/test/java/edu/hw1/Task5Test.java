package edu.hw1;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task5Test {

    private static final Task5 TASK_5 = new Task5();

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void isPalindromeDescendant(int number, boolean expected) {
        boolean actual = TASK_5.isPalindromeDescendant(number);
        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Stream<Arguments> provideParameters() {
        return Stream.of(
            Arguments.of(0, false),
            Arguments.of(1, false),
            Arguments.of(3131, true),
            Arguments.of(12, false),
            Arguments.of(123, false),
            Arguments.of(121, true),
            Arguments.of(9901, true),
            Arguments.of(9981, false),
            Arguments.of(11211230, true),
            Arguments.of(13001120, true),
            Arguments.of(23336014, true),
            Arguments.of(11, true),
            Arguments.of(Integer.MAX_VALUE, false)
        );
    }

}
