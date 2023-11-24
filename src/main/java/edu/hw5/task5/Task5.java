package edu.hw5.task5;

import org.jetbrains.annotations.NotNull;

public class Task5 {
    @SuppressWarnings("LineLength")
    private static final String RUSSIAN_CAR_NUMBER_PATTERN =
        "^[АВЕКМНОРСТУХ]([1-9]\\d\\d|\\d[1-9]\\d|\\d\\d[1-9])[АВЕКМНОРСТУХ]{2}(0[1-9]|[1-9]\\d|[1-9]\\d[1-9]|[1-9][1-9]\\d)$";

    public boolean isRussianCarNumber(@NotNull String number) {
        return number.matches(RUSSIAN_CAR_NUMBER_PATTERN);
    }
}
