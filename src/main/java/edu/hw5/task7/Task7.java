package edu.hw5.task7;

import org.jetbrains.annotations.NotNull;

public class Task7 {
    public boolean doesBinaryStringHaveAtThirdSymbolZero(@NotNull String binaryString) {
        return binaryString.matches("^[01]{2}0[01]*$");
    }

    public boolean doesBinaryStringHaveEqualsStartEndSymbol(String binaryString) {
        return binaryString.matches("^([01])[01]*\\1$");
    }

    public boolean doesBinaryStringHaveLengthBetweenOneAndThree(String binaryString) {
        return binaryString.matches("[01]{1,3}");
    }
}
