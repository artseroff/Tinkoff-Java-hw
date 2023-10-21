package edu.hw1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Task6 {
    private static final int KAPREKARS_NUMBER = 6174;
    private static final int DIGITS_COUNT = 4;
    private static final int NUMBER_SYSTEM = 10;

    public int countK(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Number must be positive");
        }
        if ((int) Math.log10(n) + 1 != DIGITS_COUNT) {
            throw new IllegalArgumentException("Number must be more than 999");
        }
        List<Integer> digits = makeListOfDigitsFromNumber(n);
        digits.sort(Comparator.comparingInt(o -> o));
        if (digits.getFirst().equals(digits.getLast())) {
            throw new IllegalArgumentException("The number must have at least 2 different digits");
        }
        return kRecursive(n);
    }

    private int kRecursive(int n) {
        List<Integer> digits = makeListOfDigitsFromNumber(n);
        digits.sort(Comparator.comparingInt(o -> o));

        int nAsc = makeNumberFromListOfDigits(digits);
        digits.sort(Comparator.comparingInt(o -> -o));
        int nDesc = makeNumberFromListOfDigits(digits);
        int newN = nDesc - nAsc;
        if (newN == KAPREKARS_NUMBER) {
            return 1;
        }
        return kRecursive(newN) + 1;
    }

    private List<Integer> makeListOfDigitsFromNumber(int number) {
        int n = number;
        List<Integer> digits = new ArrayList<>();
        while (n > 0) {
            digits.addFirst(n % NUMBER_SYSTEM);
            n /= NUMBER_SYSTEM;
        }
        return digits;
    }

    private int makeNumberFromListOfDigits(List<Integer> digits) {
        int number = 0;
        for (int digit : digits) {
            number = number * NUMBER_SYSTEM + digit;
        }
        return number;
    }
}
