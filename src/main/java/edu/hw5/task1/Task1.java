package edu.hw5.task1;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public class Task1 {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");

    public Duration calculateAnalytic(@NotNull Set<String> strings) {
        try {
            long minutesAmount = 0;
            for (String elString : strings) {
                String[] dateTimes = elString.split(" - ");
                TemporalAccessor parsedStartTime = DATE_TIME_FORMATTER.parse(dateTimes[0]);
                LocalDateTime startTime = LocalDateTime.from(parsedStartTime);

                TemporalAccessor parsedEndTime = DATE_TIME_FORMATTER.parse(dateTimes[1]);
                LocalDateTime endTime = LocalDateTime.from(parsedEndTime);

                if (!startTime.isBefore(endTime)) {
                    throw new IllegalArgumentException("Start date parameter must be less than end date");
                }
                minutesAmount += Duration.between(startTime, endTime).toSeconds();
            }

            return Duration.ofSeconds(minutesAmount / strings.size());
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Illegal date string input");
        }

    }
}
