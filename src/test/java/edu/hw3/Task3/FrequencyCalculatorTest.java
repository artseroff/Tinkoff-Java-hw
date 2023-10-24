package edu.hw3.Task3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FrequencyCalculatorTest {

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void freqDict(String[] input, String expected) {
        var calculator = new FrequencyCalculator<String>();
        String actual = calculator.freqDict(input);
        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] provideParameters() {
        return new Arguments[] {
            Arguments.of(new String[] {"a", "bb", "a", "bb"}, "{bb: 2, a: 2}"),
        };
    }
}
