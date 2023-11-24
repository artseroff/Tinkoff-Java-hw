package edu.hw5.task4;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task4Test {
    private static final Task4 TASK_4 = new Task4();

    @ParameterizedTest
    @MethodSource("params")
    void isValidPassword(String password, boolean expected) {
        boolean actual = TASK_4.isValidPassword(password);
        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] params() {
        return new Arguments[] {
            Arguments.of("~!@#$%^&*|", true),
            Arguments.of("~abc", true),
            Arguments.of("abc!", true),
            Arguments.of("a@c", true),
            Arguments.of("q#erty", true),
            Arguments.of("qw$r%y", true),
            Arguments.of("qwe^ty", true),
            Arguments.of("qw&r|y", true),
            Arguments.of("qwerty", false),
            Arguments.of(" ", false),
            Arguments.of("", false)
        };
    }
}
