package edu.hw5.task6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task6Test {

    private static final Task6 TASK_6 = new Task6();

    @ParameterizedTest
    @MethodSource("isSubsequenceParameters")
    public void isSubsequence(String string, String subsequence, boolean expected) {
        Assertions.assertEquals(expected, TASK_6.isSubsequence(string, subsequence));
    }

    private static Arguments[] isSubsequenceParameters() {
        return new Arguments[] {
            Arguments.of("achfdbaabgabcaabg", "abc", true),
            Arguments.of("abacaba", "abc", true),
            Arguments.of("aboba", "abc", false),
            Arguments.of("1b2a3c45", "abc", false),
            Arguments.of("a12bc345", "abc", true),
            Arguments.of("a.bcd*", ".*", true),
            Arguments.of("alphabet is [a-c]", "[a-c]", true),
            Arguments.of("alphabet", "beta", false),
            Arguments.of("alphabet", "", true)

        };
    }
}
