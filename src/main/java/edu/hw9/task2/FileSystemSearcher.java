package edu.hw9.task2;

import edu.hw6.task3.AbstractPathFilter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import org.jetbrains.annotations.NotNull;

public class FileSystemSearcher {
    private FileSystemSearcher() {

    }

    public static List<Path> searchDirectories(Path root, int requiredFilesNumber) {
        checkIsDirectoryOrThrow(root);
        List<Path> directoriesContainingMinimumFilesCount = Collections.synchronizedList(new LinkedList<>());
        try (var forkJoinPool = new ForkJoinPool()) {
            SearchDirectoryTask task = new SearchDirectoryTask(root, requiredFilesNumber,
                directoriesContainingMinimumFilesCount
            );
            forkJoinPool.invoke(task);
            return directoriesContainingMinimumFilesCount;
        }
    }

    public static List<Path> searchFiles(Path root, AbstractPathFilter filter) {
        checkIsDirectoryOrThrow(root);
        try (var forkJoinPool = new ForkJoinPool()) {
            SearchFileTask task = new SearchFileTask(root, filter);
            return forkJoinPool.invoke(task);
        }
    }

    private static void checkIsDirectoryOrThrow(@NotNull Path path) {
        if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Переданный путь должен быть папкой");
        }
    }
}
