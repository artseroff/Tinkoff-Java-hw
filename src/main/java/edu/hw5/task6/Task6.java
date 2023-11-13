package edu.hw5.task6;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;

public class Task6 {
    public boolean isSubsequence(@NotNull String string, @NotNull String subsequence) {
        List<String> proceedSubsequenceSymbols = Arrays.stream(subsequence.split(""))
            .map(Pattern::quote)
            .toList();
        String patternText = String.join(".*", proceedSubsequenceSymbols);
        Pattern pattern = Pattern.compile(patternText);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }

}
