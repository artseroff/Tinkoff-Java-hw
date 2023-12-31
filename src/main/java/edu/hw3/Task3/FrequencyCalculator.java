package edu.hw3.Task3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyCalculator<T> {
    private final Map<T, Integer> dict = new HashMap<>();

    public Map<T, Integer> freqDict(T[] args) {
        for (T elObject : args) {
            Integer foundCount = dict.get(elObject);
            dict.put(elObject, foundCount == null ? 1 : foundCount + 1);
        }
        return Collections.unmodifiableMap(dict);
    }

    @Override
    public String toString() {
        List<String> strings = new ArrayList<>();
        for (var elEntry : dict.entrySet()) {
            strings.add("%s: %d".formatted(elEntry.getKey(), elEntry.getValue()));
        }
        return "{%s}".formatted(String.join(", ", strings));
    }
}
