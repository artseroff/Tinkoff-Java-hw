package edu.hw8.task2;

import org.jetbrains.annotations.NotNull;

public interface ThreadPool extends AutoCloseable {

    void start();

    void execute(@NotNull Runnable runnable);
}
