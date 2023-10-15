package edu.hw1;

import org.jetbrains.annotations.NotNull;

public class Task1 {

    private static final int SECONDS_PER_MINUTE = 60;

    private static final String TIME_STRING_REGEX = "\\d{2,}:[0-5]\\d";

    public long minutesToSeconds(@NotNull String timeText) {

        if (!timeText.matches(TIME_STRING_REGEX)) {
            return -1L;
        }

        String[] stringNumbers = timeText.split(":");

        int seconds = Integer.parseInt(stringNumbers[1]);
        // only minutes inside try-catch because there is can be value more than max int
        int minutes;
        try {
            minutes = Integer.parseInt(stringNumbers[0]);
        } catch (NumberFormatException ex) {
            return -1L;
        }

        return seconds + (long) (minutes * SECONDS_PER_MINUTE);
    }
}
