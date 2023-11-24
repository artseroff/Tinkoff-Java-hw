package edu.hw5.task8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task8Test {
    private static final Task8 TASK_8 = new Task8();

    @ParameterizedTest
    @MethodSource("doesBinaryStringHaveAtThirdSymbolZeroParameters")
    public void doesBinaryStringHaveAtThirdSymbolZero(String binaryString, boolean expected) {
        Assertions.assertEquals(expected, TASK_8.doesEveryOddSymbolEqualOne(binaryString));
    }

    private static Arguments[] doesBinaryStringHaveAtThirdSymbolZeroParameters() {
        return new Arguments[] {
            Arguments.of("", false),
            Arguments.of("1", true),
            Arguments.of("10", true),
            Arguments.of("101", true),
            Arguments.of("1011", true),
            Arguments.of("101110", true),
            Arguments.of("010", false),
            Arguments.of("01011", false)
        };
    }

    @ParameterizedTest
    @MethodSource("doesBinaryStringHaveOddLengthParameters")
    public void doesBinaryStringHaveOddLength(String binaryString, boolean expected) {
        Assertions.assertEquals(expected, TASK_8.doesBinaryStringHaveOddLength(binaryString));
    }

    private static Arguments[] doesBinaryStringHaveOddLengthParameters() {
        return new Arguments[] {
            Arguments.of("", false),
            Arguments.of("1", true),
            Arguments.of("10", false),
            Arguments.of("101", true),
            Arguments.of("1011", false),
            Arguments.of("10111", true),
            Arguments.of(" ", false)
        };
    }

    @ParameterizedTest
    @MethodSource("startWithZeroOddLenOrStartWithOneEvenLenParameters")
    public void doesBinaryStringStartWithZeroAndHaveOddLenOrStartWithOneAndHaveEven(
        String binaryString,
        boolean expected
    ) {
        Assertions.assertEquals(
            expected,
            TASK_8.doesBinaryStringStartWithZeroAndHaveOddLenOrStartWithOneAndHaveEven(binaryString)
        );
    }

    private static Arguments[] startWithZeroOddLenOrStartWithOneEvenLenParameters() {
        return new Arguments[] {
            Arguments.of("", false),
            Arguments.of("0", true),
            Arguments.of("1", false),
            Arguments.of("10", true),
            Arguments.of("00", false),
            Arguments.of("010", true),
            Arguments.of("100", false),
            Arguments.of("1111", true),
            Arguments.of("0000", false),
            Arguments.of("00100", true),
            Arguments.of("100100", true),
            Arguments.of(" ", false)
        };
    }

    @ParameterizedTest
    @MethodSource("doesBinaryStringNotContainsDoubleOnesOrTripleOnesParameters")
    public void doesBinaryStringNotContainsDoubleOnesOrTripleOnes(
        String binaryString, boolean expected
    ) {
        Assertions.assertEquals(
            expected,
            TASK_8.doesBinaryStringNotContainsDoubleOnesOrTripleOnes(binaryString)
        );
    }

    private static Arguments[] doesBinaryStringNotContainsDoubleOnesOrTripleOnesParameters() {
        return new Arguments[] {
            Arguments.of("", true),
            Arguments.of("01", true),
            Arguments.of("11", false),
            Arguments.of("0111", false),
            Arguments.of("011", false),
            Arguments.of("10110", false),
            Arguments.of("010101111011", false),
            Arguments.of("010101", true),
            Arguments.of(" ", false)
        };
    }
}

