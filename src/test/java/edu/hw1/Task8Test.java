package edu.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

class Task8Test {

    private static final Task8 TASK_8 = new Task8();

    @ParameterizedTest
    @MethodSource("provideParametersProperSituation")
    public void knightBoardCapture(int[][] field, boolean expected) {
        boolean actual = TASK_8.knightBoardCapture(field);
        Assertions.assertEquals(expected,actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Stream<Arguments> provideParametersProperSituation() {
        return Stream.of(
            Arguments.of(new int[][]
                {{0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 1, 0, 1, 0},
                    {0, 1, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0, 0, 1},
                    {0, 0, 0, 0, 1, 0, 0, 0}}, true),
            Arguments.of(new int[][]
                {{1, 0, 1, 0, 1, 0, 1, 0},
                    {0, 1, 0, 1, 0, 1, 0, 1},
                    {0, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 1, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 0, 0, 0, 1, 0, 1},
                    {1, 0, 0, 0, 1, 0, 1, 0},
                    {0, 0, 0, 1, 0, 1, 0, 1}}, false),
            Arguments.of(new int[][]
                {{0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 1, 0, 0, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 0, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 0, 1, 0, 0},
                    {1, 0, 0, 0, 0, 0, 0, 0}}, false)
        );
    }

    @Test
    public void knightBoardCaptureNullInput() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_8.knightBoardCapture(null));

    }
}
