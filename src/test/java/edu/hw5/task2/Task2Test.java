package edu.hw5.task2;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task2Test {

    private static final Task2 TASK_2 = new Task2();

    @ParameterizedTest
    @MethodSource("nextThirteenFridays")
    public void calculateNextThirteenFriday(LocalDate date, LocalDate expected) {
        LocalDate actual = TASK_2.calculateNextThirteenFriday(date);

        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] nextThirteenFridays() {
        return new Arguments[] {
            Arguments.of(LocalDate.of(2022, 12, 12), LocalDate.of(2023, 1, 13)),
            Arguments.of(LocalDate.of(2024, 9, 12), LocalDate.of(2024, 9, 13)),
            Arguments.of(LocalDate.of(2024, 9, 13), LocalDate.of(2024, 12, 13)),
            Arguments.of(LocalDate.of(2024, 9, 14), LocalDate.of(2024, 12, 13)),
            Arguments.of(LocalDate.of(1925, 2, 13), LocalDate.of(1925, 3, 13))
        };
    }

    @ParameterizedTest
    @MethodSource("thirteenFridaysListByYearParameters")
    public void buildThirteenFridaysList(int year, List<LocalDate> expected) {
        List<LocalDate> actual = TASK_2.buildThirteenFridaysList(year);

        Assertions.assertEquals(expected, actual);
    }

    private static Arguments[] thirteenFridaysListByYearParameters() {
        return new Arguments[] {
            Arguments.of(2023, List.of(LocalDate.of(2023, 1, 13), LocalDate.of(2023, 10, 13))),
            Arguments.of(2024, List.of(LocalDate.of(2024, 9, 13), LocalDate.of(2024, 12, 13))),
            Arguments.of(
                1925,
                List.of(LocalDate.of(1925, 2, 13), LocalDate.of(1925, 3, 13), LocalDate.of(1925, 11, 13))
            )
        };
    }

    @ParameterizedTest
    @MethodSource("thirteenFridaysListInvalidYearParameters")
    public void buildThirteenFridaysListInvalidYear(int year) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_2.buildThirteenFridaysList(year));
    }

    private static Arguments[] thirteenFridaysListInvalidYearParameters() {
        return new Arguments[] {
            Arguments.of(0),
            Arguments.of(-1),
            Arguments.of(Task2.MAX_YEAR + 1)
        };
    }
}
