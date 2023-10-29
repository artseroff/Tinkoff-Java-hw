package edu.hw3.Task3;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FrequencyCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideParametersToString")
    public void freqDictStringRepresentation(Object[] input, String expected) {

        var calculator = new FrequencyCalculator<>();
        calculator.freqDict(input);
        Assertions.assertEquals(expected, calculator.toString());
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] provideParametersToString() {
        return new Arguments[] {
            Arguments.of(new String[] {"a", "bb", "a", "bb"}, "{bb: 2, a: 2}"),
            Arguments.of(new String[] {"код", "код", "код", "bug"}, "{код: 3, bug: 1}"),
            Arguments.of(new Integer[] {2, null, 1, 3, 2, 3, 3}, "{null: 1, 1: 1, 2: 2, 3: 3}")
        };
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void freqDict(Object[] input, Map<?, Integer> expected) {
        var calculator = new FrequencyCalculator<>();

        Assertions.assertEquals(expected, calculator.freqDict(input));
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] provideParameters() {
        return new Arguments[] {
            Arguments.of(new String[] {"a", "bb", "a", "bb"}, Map.of("bb", 2, "a", 2)),
            Arguments.of(new String[] {"код", "код", "код", "bug"}, Map.of("код", 3, "bug", 1)),
            Arguments.of(new Integer[] {2, 1, 3, 2, 3, 3}, Map.of( 1, 1, 2, 2, 3, 3))
        };
    }
}
