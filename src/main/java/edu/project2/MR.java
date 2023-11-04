package edu.project2;

import java.util.List;

public class MR {
    private List<Integer> arr = List.of(
        0, 1, 0, 0, 1, 1, 0,
        1, 0, 0,
        0, 0, 1, 1, 0, 1, 0, 1, 1, 0,
        1, 0,
        1, 1, 0, 0, 0, 0, 0,
        1,
        0, 1, 1, 0, 0, 1, 0,
        1,
        1,
        0,
        0, 0, 1, 0, 1, 1,
        1,
        0
    );
    private int ind = 0;

    public boolean nextBoolean() {
        int val;

        val = arr.get(ind);
        ind++;
        return val == 1;
    }
}
