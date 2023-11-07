package edu.hw3.Task8;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.List;

public class BackwardIteratorTest {
    @ParameterizedTest
    @MethodSource("provideParameters")
    public void backwardIterator(List<Object> list) {
        var iter = new BackwardIterator<>(list);
        Assertions.assertEquals(!(list.isEmpty()), iter.hasNext());
        int i = 1;
        while (iter.hasNext()) {
            Assertions.assertEquals(list.get(list.size()-i), iter.next());
            i++;
        }

    }

    @SuppressWarnings("MagicNumber")
    private static Arguments[] provideParameters() {
        return new Arguments[] {
            Arguments.of(List.of(1,2,3)),
            Arguments.of(List.of("small","middle", "big")),
            Arguments.of(List.of())

        };
    }
}
