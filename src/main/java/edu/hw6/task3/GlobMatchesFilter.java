package edu.hw6.task3;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import org.jetbrains.annotations.NotNull;

public class GlobMatchesFilter implements AbstractPathFilter {
    private final PathMatcher matcher;

    public GlobMatchesFilter(@NotNull String glob) {
        matcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
    }

    @Override
    public boolean accept(Path path) {
        return matcher.matches(path.getFileName());
    }
}
