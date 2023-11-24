package edu.hw6.task3;

import java.nio.file.Files;
import org.jetbrains.annotations.NotNull;

public final class PathFilters {

    private PathFilters() {
    }

    public static final AbstractPathFilter WRITABLE = Files::isWritable;
    public static final AbstractPathFilter READABLE = Files::isReadable;

    public static AbstractPathFilter largerThan(long size) {
        return entry -> Files.size(entry) > size;
    }

    public static AbstractPathFilter lessThan(long size) {
        return entry -> Files.size(entry) < size;
    }

    public static AbstractPathFilter regexMatches(@NotNull String regex) {
        return entry -> entry.getFileName().toString().matches(regex);
    }

}
