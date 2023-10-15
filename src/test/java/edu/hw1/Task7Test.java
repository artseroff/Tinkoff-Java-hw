package edu.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class Task7Test {

    private static final Task7 TASK_7 = new Task7();

    @ParameterizedTest
    @MethodSource("provideParametersRotateRight")
    public void rotateRightPositiveNumber(int number, int shift, int expected) {
        int actual = TASK_7.rotateRight(number, shift);
        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideParametersRotateRight")
    public void rotateLeftPositiveNumber(int number, int shift, int expected) {
        int actual = TASK_7.rotateLeft(number, -shift);
        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Stream<Arguments> provideParametersRotateRight() {
        return Stream.of(
            Arguments.of(0, 1, 0),
            Arguments.of(9, 0, 9),
            Arguments.of(8, 1, 4),
            Arguments.of(8, 5, 4),
            Arguments.of(8, 4, 8),
            Arguments.of(16, -1, 1),
            Arguments.of(17, -2, 6),
            Arguments.of(17, 1, 24),
            Arguments.of(1024, 11, 1024),
            Arguments.of(1024, 10, 1),
            Arguments.of(1024, -10, 512)
        );
    }

    @Test
    public void testRotatesNegativeNumber() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_7.rotateRight(-1, 1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_7.rotateRight(-1, 1));
    }
}
