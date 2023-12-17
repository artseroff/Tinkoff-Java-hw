package edu.hw10.task1.arguments;

import java.lang.annotation.Annotation;

public interface RandomArgumentGenerator {
    Object next(Annotation[] annotations);
}
