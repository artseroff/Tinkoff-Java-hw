package edu.hw3.Task7;

import java.util.Comparator;
import java.util.TreeMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NullWrapComparatorTest {

    @Test
    public void canPutNullTest() {
        TreeMap<String, String>
            tree = new TreeMap<>(new NullWrapComparator<String>(false, Comparator.naturalOrder()));
        tree.put(null, "s");
        Assertions.assertTrue(tree.containsKey(null));
    }

    @Test
    public void nullFirstTest() {
        TreeMap<Integer, Integer>
            tree = new TreeMap<>(new NullWrapComparator<Integer>(true, Comparator.naturalOrder()));
        tree.put(1, 1);
        tree.put(null, 2);
        tree.put(3, 3);
        Assertions.assertNull(tree.firstEntry().getKey());
    }

    @Test
    public void nullLastTest() {
        TreeMap<Integer, Integer>
            tree = new TreeMap<>(new NullWrapComparator<Integer>(false, Comparator.naturalOrder()));
        tree.put(1, 1);
        tree.put(null, 2);
        tree.put(3, 3);
        Assertions.assertNull(tree.lastEntry().getKey());
    }

    @Test
    public void sortWithNulls() {
        Integer[] expected = new Integer[] {null, 1, 2, 3};

        TreeMap<Integer, Integer>
            tree = new TreeMap<>(new NullWrapComparator<Integer>(true, Comparator.naturalOrder()));
        tree.put(1, 1);
        tree.put(null, 2);
        tree.put(3, 3);
        tree.put(2, 2);

        Assertions.assertArrayEquals(expected, tree.keySet().toArray());
    }

    @Test
    public void nullWrappedComparator() {
        Integer[] expected = new Integer[] {null, 3};

        TreeMap<Integer, Integer>
            tree = new TreeMap<>(new NullWrapComparator<>(true, null));
        tree.put(3, 1);
        tree.put(null, 2);

        Assertions.assertArrayEquals(expected, tree.keySet().toArray());
    }
}
