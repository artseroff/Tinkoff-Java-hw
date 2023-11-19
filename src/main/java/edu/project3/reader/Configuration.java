package edu.project3.reader;

import edu.project3.writer.Extension;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public record Configuration(String path, LocalDateTime startDate, LocalDateTime endDate, Extension extension) {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Configuration(String path, LocalDateTime startDate, LocalDateTime endDate, Extension extension) {
        this.path = path;
        this.startDate = startDate;
        this.endDate = endDate;
        this.extension = extension == null ? Extension.MARKDOWN : extension;
    }

    @SuppressWarnings("InnerAssignment")
    public static Configuration parseArgs(String[] args) throws IllegalConfigurationException {
        String path;
        LocalDate startDate = null;
        LocalDate endDate = null;
        Extension extension = null;
        try {
            if ("--path".equals(args[0])) {
                path = args[1];
            } else {
                throw new IllegalConfigurationException("Не задан путь к логам");
            }


            for (int i = 2; i < args.length; i += 2) {
                String argValue = args[i + 1];
                switch (args[i]) {
                    case "--from" -> startDate = parseStartDate(startDate, argValue);
                    case "--to" -> endDate = parseEndDate(endDate, argValue);
                    case "--format" -> extension = parseExtension(extension, argValue);
                    case null, default -> throw new IllegalConfigurationException("Неверное имя аргумента");
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalConfigurationException("Не задано значение аргумента");
        } catch (DateTimeParseException e) {
            throw new IllegalConfigurationException("Неверный формат даты");
        }
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalConfigurationException("Начальная дата больше конечной");
        }
        return new Configuration(
            path,
            startDate == null ? LocalDateTime.MIN : startDate.atStartOfDay(),
            endDate == null ? LocalDateTime.MAX : endDate.plusDays(1).atStartOfDay().minusNanos(1),
            extension
        );
    }

    private static LocalDate parseStartDate(LocalDate startDate, String argValue) throws IllegalConfigurationException {
        if (startDate != null) {
            throw new IllegalConfigurationException("Начальная дата уже задана");
        }
        return LocalDate.parse(argValue, DATE_FORMAT);
    }

    private static LocalDate parseEndDate(LocalDate endDate, String argValue) throws IllegalConfigurationException {
        if (endDate != null) {
            throw new IllegalConfigurationException("Конечная дата уже задана");
        }
        return LocalDate.parse(argValue, DATE_FORMAT);
    }

    private static Extension parseExtension(Extension extension, String argValue) throws IllegalConfigurationException {
        if (extension != null) {
            throw new IllegalConfigurationException("Формат вывода уже задан");
        }
        return Extension.parse(argValue);
    }
}
