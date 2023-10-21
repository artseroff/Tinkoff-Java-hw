package edu.hw1;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task2Test {

    private static final Task2 TASK_2 = new Task2();

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void countDigits(int input, int expected) {
        int actual = TASK_2.countDigits(input);
        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Stream<Arguments> provideParameters() {
        return Stream.of(
            Arguments.of(0, 1),
            Arguments.of(-5678, 4),
            Arguments.of(5678, 4),
            Arguments.of(Integer.MAX_VALUE, 10),
            Arguments.of(Integer.MIN_VALUE, 10)
        );
    }
}
