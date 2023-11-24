package edu.hw5.task8;

public class Task8 {
    public boolean doesEveryOddSymbolEqualOne(String binaryString) {
        return binaryString.matches("^(1([01]|$))+$");
    }

    public boolean doesBinaryStringHaveOddLength(String binaryString) {
        return binaryString.matches("^[01]([01][01])*$");
    }

    public boolean doesBinaryStringStartWithZeroAndHaveOddLenOrStartWithOneAndHaveEven(String binaryString) {
        return binaryString.matches("^(0([01][01])*|1[01]([01][01])*)$");
    }

    public boolean doesBinaryStringNotContainsDoubleOnesOrTripleOnes(String binaryString) {
        return binaryString.matches("^(?![01]*11|[01]*111)[01]*$");
    }

}
