package edu.hw6;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class FilePathChecker {
    private FilePathChecker() {

    }

    public static void checkFilePath(Path path, boolean needCheckExistence) throws NoSuchFileException {
        if (needCheckExistence && !Files.exists(path)) {
            throw new NoSuchFileException("File not found");
        }
        if (Files.isDirectory(path)) {
            throw new IllegalArgumentException("Path must be not directory");
        }
    }

    public static void checkFilePath(Path path) throws NoSuchFileException {
        checkFilePath(path, true);
    }
}
