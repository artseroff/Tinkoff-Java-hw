package edu.hw1;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class Task6Test {

    private static final Task6 TASK_6 = new Task6();

    @ParameterizedTest @MethodSource("provideParametersProperSituation") public void countKTest(int n, int expected) {
        int actual = TASK_6.countK(n);
        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber") private static Stream<Arguments> provideParametersProperSituation() {
        return Stream.of(Arguments.of(3524, 3), Arguments.of(6621, 5), Arguments.of(6554, 4), Arguments.of(1234, 3));
    }

    @Test public void countKNegativeExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_6.countK(-1));
    }

    @Test public void countKNot4DigitsExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_6.countK(1));
    }

    @SuppressWarnings("MagicNumber")
    @Test public void countKSameDigitsExceptionTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_6.countK(1111));
    }
}
