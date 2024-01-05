package edu.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;

public class Task3TestTest {

    private static final Task3 TASK_3 = new Task3();

    @ParameterizedTest
    @MethodSource("provideParametersProperSituation")
    public void isNestableProperSituation(int[] a1, int[] a2, boolean expected) {
        boolean actual = TASK_3.isNestable(a1,a2);
        Assertions.assertEquals(expected,actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Stream<Arguments> provideParametersProperSituation() {
        return Stream.of(
            Arguments.of(new int[]{-1, 2, 3, 4}, new int[]{-5, 6}, true),
            Arguments.of(new int[]{4, 2}, new int[]{5, 1}, true),
            Arguments.of(new int[]{2, 1, 2, 5}, new int[]{9, 0}, true),
            Arguments.of(new int[]{1, 2}, new int[]{}, false),
            Arguments.of(new int[]{}, new int[]{2,3}, false),
            Arguments.of(new int[]{}, new int[]{}, false),
            Arguments.of(new int[]{1,1}, new int[]{0, 3}, true),
            Arguments.of(new int[]{1,1}, new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}, true)
            );
    }

    @Test
    public void isNestableNullInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_3.isNestable(null, null));
    }

}
