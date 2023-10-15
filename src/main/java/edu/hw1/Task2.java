package edu.hw1;

public class Task2 {

    public int countDigits(int number) {
        if (number == 0) {
            return 1;
        }

        int absoluteValue;

        if (number == Integer.MIN_VALUE) {
            absoluteValue = Integer.MAX_VALUE;
        } else {
            absoluteValue = number > 0 ? number : -number;
        }

        return (int) (Math.log10(absoluteValue) + 1);
    }
}
