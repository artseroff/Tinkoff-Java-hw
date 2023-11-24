package edu.hw5.task7;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task7Test {

    private static final Task7 TASK_7 = new Task7();

    @ParameterizedTest
    @MethodSource("doesBinaryStringHaveAtThirdSymbolZeroParameters")
    public void doesBinaryStringHaveAtThirdSymbolZero(String binaryString, boolean expected) {
        Assertions.assertEquals(expected, TASK_7.doesBinaryStringHaveAtThirdSymbolZero(binaryString));
    }

    private static Arguments[] doesBinaryStringHaveAtThirdSymbolZeroParameters() {
        return new Arguments[] {
            Arguments.of("000", true),
            Arguments.of("011", false),
            Arguments.of("010", true),
            Arguments.of("0001", true),
            Arguments.of("000101010", true),
            Arguments.of("0111", false),
            Arguments.of(" ", false),
            Arguments.of("", false)

        };
    }

    @ParameterizedTest
    @MethodSource("doesBinaryStringHaveEqualsStartEndSymbolsParameters")
    public void doesBinaryStringHaveEqualsStartEndSymbols(String binaryString, boolean expected) {
        Assertions.assertEquals(expected, TASK_7.doesBinaryStringHaveEqualsStartEndSymbol(binaryString));
    }

    private static Arguments[] doesBinaryStringHaveEqualsStartEndSymbolsParameters() {
        return new Arguments[] {
            Arguments.of("000", true),
            Arguments.of("011", false),
            Arguments.of("111", true),
            Arguments.of("0001", false),
            Arguments.of("10101", true),
            Arguments.of("0111", false),
            Arguments.of("00", true),
            Arguments.of("1", false),
            Arguments.of("", false)
        };
    }

    @ParameterizedTest
    @MethodSource("doesBinaryStringHaveLengthBetweenOneAndThreeParameters")
    public void doesBinaryStringHaveLengthBetweenOneAndThree(String binaryString, boolean expected) {
        Assertions.assertEquals(expected, TASK_7.doesBinaryStringHaveLengthBetweenOneAndThree(binaryString));
    }

    private static Arguments[] doesBinaryStringHaveLengthBetweenOneAndThreeParameters() {
        return new Arguments[] {
            Arguments.of(" ", false),
            Arguments.of("01", true),
            Arguments.of("000", true),
            Arguments.of("0001", false),
            Arguments.of("10101", false),
            Arguments.of("011111", false)

        };
    }

}
