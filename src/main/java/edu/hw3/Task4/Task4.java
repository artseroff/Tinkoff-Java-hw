package edu.hw3.Task4;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Task4 {
    private static final Map<Integer, String> ROMAN_DIGITS = new TreeMap<>(Comparator.reverseOrder()) {{
        put(1000, "M");
        put(900, "CM");
        put(500, "D");
        put(400, "CD");
        put(100, "C");
        put(90, "XC");
        put(50, "L");
        put(40, "XL");
        put(10, "X");
        put(9, "IX");
        put(5, "V");
        put(4, "IV");
        put(1, "I");
    }};

    public String convertToRoman(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Number must be > 0");
        }
        int number = n;
        StringBuilder result = new StringBuilder();
        for (int k : ROMAN_DIGITS.keySet()) {
            while (number >= k) {
                number -= k;
                result.append(ROMAN_DIGITS.get(k));
            }
        }
        return result.toString();
    }
}
