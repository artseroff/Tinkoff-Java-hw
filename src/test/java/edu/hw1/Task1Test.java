package edu.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Task1Test {

    private static final Task1 TASK_1 = new Task1();

    @ParameterizedTest
    @CsvSource(value = {
        "01:00, 60",
        "02:30, 150",
        "00:35, 35",
        "13:56, 836",
        "00:00, 0",
        Integer.MAX_VALUE + ":00, " + Integer.MAX_VALUE * 60,
    })
    public void minutesToSecondsProperInput(String input, long expectedResult) {

        long actualResult = TASK_1.minutesToSeconds(input);

        Assertions.assertEquals(expectedResult, actualResult);

    }

    @ParameterizedTest
    @ValueSource(strings = {"", "abac", ":", "0:0", "ab:ac", "01:a", "a:01", "999:60", "-1:00", "00:-1", "-1:-1",
        Integer.MIN_VALUE + ":0", Integer.MAX_VALUE + "9999:0"})
    public void minutesToSecondsInvalidInput(String input) {

        long actualResult = TASK_1.minutesToSeconds(input);

        Assertions.assertEquals(-1L, actualResult);

    }

    @Test
    public void minutesToSecondsNullInput() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_1.minutesToSeconds(null));
    }

}
