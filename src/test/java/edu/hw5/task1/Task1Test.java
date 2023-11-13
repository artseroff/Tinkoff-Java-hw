package edu.hw5.task1;

import java.time.Duration;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task1Test {

    private static final Task1 TASK_1 = new Task1();

    @ParameterizedTest
    @MethodSource("rangeDateStrings")
    public void calculateAnalytic(Set<String> input, Duration expected) {
        Assertions.assertEquals(expected, TASK_1.calculateAnalytic(input));
    }

    private static Arguments[] rangeDateStrings() {
        return new Arguments[] {
            Arguments.of(Set.of(
                "2022-03-12, 20:20 - 2022-03-12, 23:50",
                "2022-04-01, 21:30 - 2022-04-02, 01:20"
            ), Duration.ofMinutes(220)),
            Arguments.of(Set.of(
                "2022-03-12, 12:21 - 2022-03-12, 14:20",
                "2022-04-01, 12:00 - 2022-04-02, 12:00"
            ), Duration.ofSeconds(46770)),
            Arguments.of(Set.of(
                "2022-03-01, 12:22 - 2022-03-02, 12:20",
                "2022-04-01, 12:00 - 2022-04-03, 12:00"
            ), Duration.ofMinutes(2159))
        };
    }

    @ParameterizedTest
    @MethodSource("invalidRangeDateStrings")
    public void calculateAnalyticExceptionTest(Set<String> input) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_1.calculateAnalytic(input));
    }

    private static Arguments[] invalidRangeDateStrings() {
        return new Arguments[] {
            Arguments.of(Set.of(
                "2022-03-01, 23:49- 2022-03-12, 23:50"
            )),
            Arguments.of(Set.of(
                "2022-03-01, 23:55 - 2022-03-01, 23:50"
            )),
            Arguments.of(Set.of(
                "2022-03-01, 23:55 - 2022-03-1, 23:57"
            )),
            Arguments.of(Set.of(
                "2022-03-01, 23:51 - ABCD-03-12, 23:50"
            )),
            Arguments.of(Set.of(
                " - "
            )),
            Arguments.of(Set.of(
                "2022-03-01, 23:51 - 2022-03-12"
            )),
            Arguments.of(Set.of(
                " "
            )),
        };
    }

}
