package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

public class SearchDirectoryTask extends RecursiveTask<Integer> {
    private final Path root;
    private final int minimalCountFiles;

    private final List<Path> directoriesContainingMinimumFilesCount;

    public SearchDirectoryTask(
        @NotNull Path root,
        int minimalCountFiles,
        @NotNull List<Path> directoriesContainingMinimumFilesCount
    ) {
        this.root = root;
        this.minimalCountFiles = minimalCountFiles;
        this.directoriesContainingMinimumFilesCount = directoriesContainingMinimumFilesCount;
    }

    @Override
    protected Integer compute() {
        List<Path> paths;

        try (Stream<Path> directoryContent = Files.list(root)) {
            paths = directoryContent.toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int dirAndFilesCount = paths.size();

        List<SearchDirectoryTask> searchTasks = new LinkedList<>();
        for (Path path : paths) {
            if (Files.isDirectory(path)) {
                var task = new SearchDirectoryTask(path, minimalCountFiles, directoriesContainingMinimumFilesCount);
                task.fork();
                searchTasks.add(task);
            }
        }
        int countFiles = dirAndFilesCount - searchTasks.size();

        for (SearchDirectoryTask task : searchTasks) {
            countFiles += task.join();
        }
        if (countFiles > minimalCountFiles) {
            directoriesContainingMinimumFilesCount.addFirst(root);
        }

        return countFiles;
    }
}
