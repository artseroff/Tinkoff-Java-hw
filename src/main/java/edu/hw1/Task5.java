package edu.hw1;

import java.util.Arrays;

public class Task5 {

    private static final Task2 TASK_2 = new Task2();

    public boolean isPalindromeDescendant(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Input number must be > 0");
        }
        int currentNumber = number;
        int currentCountDigits = TASK_2.countDigits(currentNumber);
        while (currentCountDigits > 1) {

            if (isPalindrome(currentNumber)) {
                return true;
            }

            if (currentCountDigits % 2 != 0) {
                return false;
            }

            currentNumber = calculateDescendant(currentNumber, currentCountDigits);
            currentCountDigits = TASK_2.countDigits(currentNumber);
        }

        return false;
    }

    private int calculateDescendant(int number, int countDigits) {

        int[] digitsIntArr = Arrays.stream(String.valueOf(number).split("")).mapToInt(Integer::parseInt).toArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < countDigits; i += 2) {
            sb.append(digitsIntArr[i] + digitsIntArr[i + 1]);
        }
        return Integer.parseInt(sb.toString());
    }

    private boolean isPalindrome(int number) {
        char[] numberCharsArr = String.valueOf(number).toCharArray();
        int len = numberCharsArr.length;
        int counterMatches = 0;
        int halfLen = len / 2;
        for (int i = 0; i < halfLen; i++) {
            if (numberCharsArr[i] == numberCharsArr[len - 1 - i]) {
                counterMatches++;
            }
        }
        return counterMatches == halfLen;
    }
}
