package edu.hw3.Task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Task2 {
    private static final String INVALID_SEQUENCE = "Invalid bracket sequence";

    private static final String BLANK_INPUT = "Blank input string";

    private static final String NON_BRACKET_INPUT = "Input string must consist only of brackets";

    public String[] clusterize(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException(BLANK_INPUT);
        }
        List<String> answer = new ArrayList<>();
        Stack<Character> brackets = new Stack<>();

        char[] chars = input.toCharArray();

        int previousInd = 0;
        for (int i = 0; i < input.length(); i++) {
            char ch = chars[i];
            if (ch == '(') {
                brackets.push(ch);
            } else if (ch == ')') {
                if (!brackets.empty() && brackets.peek() == '(') {
                    brackets.pop();
                    if (brackets.empty()) {
                        answer.add(input.substring(previousInd, i + 1));
                        previousInd = i + 1;
                    }
                } else {
                    throw new IllegalArgumentException(INVALID_SEQUENCE);
                }
            } else {
                throw new IllegalArgumentException(NON_BRACKET_INPUT);
            }
        }
        if (!brackets.empty()) {
            throw new IllegalArgumentException(INVALID_SEQUENCE);
        }
        return answer.toArray(new String[0]);
    }
}
