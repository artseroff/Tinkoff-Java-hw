package edu.hw10.task1.arguments;

import edu.hw10.task1.annotations.NotNull;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StringGenerator implements RandomArgumentGenerator {
    private static final char SMALL_A = 'a';
    private static final char SMALL_Z = 'z';
    private static final int MIN_LENGTH = 1;
    private static final int MAX_LENGTH = 4;
    private static final Random RANDOM = ThreadLocalRandom.current();

    @Override
    public Object next(Annotation[] annotations) {
        boolean canBeNull = Arrays.stream(annotations)
            .noneMatch(annotation -> annotation
                .annotationType()
                .equals(NotNull.class));

        if (canBeNull && RANDOM.nextBoolean()) {
            return "";
        }

        int currentLength = RANDOM.nextInt(MIN_LENGTH, MAX_LENGTH + 1);
        var charArray = new char[currentLength];
        for (int i = 0; i < currentLength; i++) {
            charArray[i] = (char) RANDOM.nextInt(SMALL_A, SMALL_Z + 1);
        }
        return new String(charArray);
    }
}
