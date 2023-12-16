package edu.hw7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Task2Test {

    @ParameterizedTest
    @MethodSource("params")
    void factorialTest(int n, long expected) {
        long actual = Task2.factorial(n);
        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] params() {
        return new Arguments[] {
            Arguments.of(0, 1),
            Arguments.of(1, 1),
            Arguments.of(2, 2),
            Arguments.of(5, 120),
            Arguments.of(9, 362880),
            Arguments.of(13,6227020800L)
    };
    }

    @Test
    void incrementCounterTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Task2.factorial(-1));
    }
}
