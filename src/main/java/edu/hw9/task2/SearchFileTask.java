package edu.hw9.task2;

import edu.hw6.task3.AbstractPathFilter;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import org.jetbrains.annotations.NotNull;

public class SearchFileTask extends RecursiveTask<List<Path>> {
    private final Path root;

    private final AbstractPathFilter filter;

    public SearchFileTask(@NotNull Path root, @NotNull AbstractPathFilter filter) {
        this.root = root;
        this.filter = filter;
    }

    @Override
    protected List<Path> compute() {
        AbstractPathFilter folderFilter = Files::isDirectory;
        AbstractPathFilter folderOrFilter = folderFilter.or(this.filter);

        List<Path> directories = new LinkedList<>();
        List<Path> filteredFiles = new LinkedList<>();
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(root, folderOrFilter)) {
            entries.forEach(path -> {
                if (Files.isDirectory(path)) {
                    directories.add(path);
                } else {
                    filteredFiles.add(path);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<SearchFileTask> searchTasks = new LinkedList<>();
        for (Path path : directories) {
            var task = new SearchFileTask(path, folderOrFilter);
            task.fork();
            searchTasks.add(task);
        }

        for (SearchFileTask task : searchTasks) {
            var listFromRecursionTask = task.join();
            filteredFiles.addAll(listFromRecursionTask);
        }
        return filteredFiles;
    }
}
