package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task3Test {

    private static final Task3 TASK_3 = new Task3();

    @ParameterizedTest
    @MethodSource("parseDateParameters")
    public void parseDate(String string, Optional<LocalDate> expected) {
        Assertions.assertEquals(expected, TASK_3.parseDate(string));
    }

    private static Arguments[] parseDateParameters() {
        return new Arguments[] {
            Arguments.of("2020-10-10", Optional.of(LocalDate.of(2020, 10, 10))),
            Arguments.of("20-10-10", Optional.empty()),
            Arguments.of("2020-12-2", Optional.of(LocalDate.of(2020, 12, 2))),
            Arguments.of("1/3/1976", Optional.of(LocalDate.of(1976, 3, 1))),
            Arguments.of("01/3/1976", Optional.of(LocalDate.of(1976, 3, 1))),
            Arguments.of("1/3/20", Optional.of(LocalDate.of(2020, 3, 1))),
            Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("today", Optional.of(LocalDate.now())),
            Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("1 day ago", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("0 days ago", Optional.empty()),
            Arguments.of("01 days ago", Optional.empty()),
            Arguments.of("00 days ago", Optional.empty()),
            Arguments.of("2234 days ago", Optional.of(LocalDate.now().minusDays(2234))),
            Arguments.of("", Optional.empty()),
            Arguments.of(" ", Optional.empty())

        };
    }
}
