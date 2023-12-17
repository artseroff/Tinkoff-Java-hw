package edu.hw10.task1.arguments;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;

import java.lang.annotation.Annotation;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IntegerGenerator implements RandomArgumentGenerator {
    private static final Random RANDOM = ThreadLocalRandom.current();

    @Override
    public Object next(Annotation[] annotations) {
        int min = Integer.MIN_VALUE;
        int max = Integer.MAX_VALUE - 1;

        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Min.class)) {
                min = ((Min) annotation).value();
            } else if (annotation.annotationType().equals(Max.class)) {
                max = ((Max) annotation).value();
            }
        }

        return RANDOM.nextInt(min, max + 1);
    }
}
