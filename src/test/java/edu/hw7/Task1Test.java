package edu.hw7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Task1Test {

    @ParameterizedTest
    @MethodSource("validParams")
    void incrementCounterTest(int countThreads, int countIterations,int expected) throws InterruptedException {
        int actual = Task1.incrementCounter(countThreads, countIterations);
        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] validParams() {
        return new Arguments[] {
            Arguments.of(1,1,1),
            Arguments.of(2,1,2),
            Arguments.of(4,4,16),
            Arguments.of(10,100,1000)
        };
    }

    @ParameterizedTest
    @MethodSource("invalidParams")
    void incrementCounterTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Task1
            .incrementCounter(0,0));
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] invalidParams() {
        return new Arguments[] {
            Arguments.of(0,1),
            Arguments.of(1,0),
            Arguments.of(0,0),
        };
    }
}
