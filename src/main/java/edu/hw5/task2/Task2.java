package edu.hw5.task2;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public class Task2 {

    private static final int THIRTEEN_DAY_OF_MONTH = 13;
    public static final int MIN_YEAR = 1;
    public static final int MAX_YEAR = 9999;

    public LocalDate calculateNextThirteenFriday(@NotNull LocalDate date) {

        return date.with(temporal -> {
                // Не захотел использовать интерфейс temporal,
                // потому что temporal.get(ChronoField.DAY_OF_MONTH)
                // выглядит не очень, как будто к закрытым полям
                // через рефлексию обращаемся
                LocalDate localDate = LocalDate.from(temporal);
                LocalDate currentDate = LocalDate.from(temporal);
                currentDate = currentDate.withDayOfMonth(THIRTEEN_DAY_OF_MONTH);
                if (localDate.getDayOfMonth() < THIRTEEN_DAY_OF_MONTH
                    && currentDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                    return currentDate;
                }
                do {
                    currentDate = currentDate.plusMonths(1);
                } while (!currentDate.getDayOfWeek().equals(DayOfWeek.FRIDAY));
                return currentDate;
            }
        );

    }

    public List<LocalDate> buildThirteenFridaysList(int year) {
        if (year < MIN_YEAR || year > MAX_YEAR) {
            throw new IllegalArgumentException("Year must be between %d and %d".formatted(MIN_YEAR, MAX_YEAR));
        }
        LinkedList<LocalDate> dates = new LinkedList<>();
        LocalDate currentDate = LocalDate.of(year, Month.JANUARY, THIRTEEN_DAY_OF_MONTH);
        do {
            if (currentDate.getDayOfWeek().equals(DayOfWeek.FRIDAY)) {
                dates.add(currentDate);
            }
            currentDate = currentDate.plusMonths(1);
        } while (currentDate.getYear() == year);
        return dates;

    }
}
