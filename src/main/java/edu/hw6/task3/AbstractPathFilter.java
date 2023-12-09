package edu.hw6.task3;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface AbstractPathFilter extends DirectoryStream.Filter<Path> {
    default AbstractPathFilter and(@NotNull AbstractPathFilter other) {
        return p -> accept(p) && other.accept(p);
    }

    default AbstractPathFilter or(@NotNull AbstractPathFilter other) {
        return p -> accept(p) || other.accept(p);
    }
}
