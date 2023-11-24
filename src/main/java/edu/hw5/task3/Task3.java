package edu.hw5.task3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

public class Task3 {
    private static final Map<String, DateTimeFormatter> DATE_PATTERNS = Map.of(
        "\\d{4}-\\d{2}-\\d{2}", DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        "\\d{4}-\\d{1,2}-\\d{1,2}", DateTimeFormatter.ofPattern("yyyy-M-d"),
        "\\d{1,2}/\\d{1,2}/\\d{4}", DateTimeFormatter.ofPattern("d/M/yyyy"),
        "\\d{1,2}/\\d{1,2}/\\d{2}", DateTimeFormatter.ofPattern("d/M/yy")
    );

    public Optional<LocalDate> parseDate(@NotNull String string) {
        LocalDate resultDate = null;

        for (var entry : DATE_PATTERNS.entrySet()) {
            if (string.matches(entry.getKey())) {
                resultDate = LocalDate.parse(string, entry.getValue());
                break;
            }
        }
        if (string.matches("tomorrow")) {
            resultDate = LocalDate.now().plusDays(1);
        } else if (string.matches("today")) {
            resultDate = LocalDate.now();
        } else if (string.matches("yesterday") || string.matches("1 day ago")) {
            resultDate = LocalDate.now().minusDays(1);
        } else if (string.matches("([2-9]|[1-9]\\d+) days ago")) {
            int days = Integer.parseInt(string.split(" ")[0]);
            resultDate = LocalDate.now().minusDays(days);
        }

        return Optional.ofNullable(resultDate);
    }
}
