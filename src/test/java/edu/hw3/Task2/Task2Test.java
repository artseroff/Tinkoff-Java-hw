package edu.hw3.Task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class Task2Test {
    private static final Task2 TASK_2 = new Task2();

    @ParameterizedTest
    @MethodSource("validBrackets")
    public void clusterizeValidInputTest(String input, String[] expected) {
        String[] actual = TASK_2.clusterize(input);
        Assertions.assertArrayEquals(expected, actual);
    }

    private static Stream<Arguments> validBrackets() {
        return Stream.of(
            Arguments.of("()()()", new String[] {"()", "()", "()"}),
            Arguments.of("((()))", new String[] {"((()))"}),
            Arguments.of("(())()(()())", new String[] {"(())", "()", "(()())"}),
            Arguments.of("((())())(()(()))", new String[] {"((())())", "(()(()))"})
        );
    }

    @ParameterizedTest
    @MethodSource("invalidBrackets")
    public void clusterizeInvalidInputTest(String input) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_2.clusterize(input));
    }

    private static Stream<Arguments> invalidBrackets() {
        return Stream.of(
            Arguments.of(")()"),
            Arguments.of("(()))"),
            Arguments.of("((())"),
            Arguments.of("()()("),
            Arguments.of("1234"),
            Arguments.of("(1)"),
            Arguments.of("   ")
        );
    }

    @Test
    public void clusterizeNullExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_2.clusterize(null));
    }
}
