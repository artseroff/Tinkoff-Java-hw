package edu.hw9.task2;

import edu.hw6.task3.AbstractPathFilter;
import edu.hw6.task3.GlobMatchesFilter;
import edu.hw6.task3.PathFilters;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileSystemSearcherTest {
    @TempDir
    Path tempDir;

    void createFiles() throws IOException {
        Path test1Dir = Files.createDirectory(tempDir.resolve("test1"));
        Path test11Dir = Files.createDirectory(test1Dir.resolve("test1_1"));
        Path test111Dir = Files.createDirectory(test11Dir.resolve("test1_1_1"));

        Path test1File = Files.createFile(tempDir.resolve("test1File.txt"));
        Files.write(test1File, new byte[15]);

        Path test11File = Files.createFile(test1Dir.resolve("test1_1File.bmp"));
        Files.write(test11File, new byte[4]);

        Path test111File = Files.createFile(test11Dir.resolve("test1_1_1File.txt"));
        Files.write(test111File, new byte[3]);

        Path test1111File = Files.createFile(test111Dir.resolve("test1_1_1_1File.txt"));
        Files.write(test1111File, new byte[4]);

    }

    @Test
    void searchDirectories() throws IOException {
        //Arrange
        createFiles();

        var expected = List.of(tempDir, tempDir.resolve("test1"));

        //Act
        var actual = FileSystemSearcher.searchDirectories(tempDir, 2);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void searchFiles() throws IOException {
        //Arrange
        createFiles();
        var expected = List.of(
            tempDir.resolve("test1").resolve("test1_1File.bmp"),
            tempDir.resolve("test1").resolve("test1_1").resolve("test1_1_1").resolve("test1_1_1_1File.txt")
        );

        GlobMatchesFilter txtFilter = new GlobMatchesFilter("*.txt");
        GlobMatchesFilter bmpFilter = new GlobMatchesFilter("*.bmp");
        AbstractPathFilter largerThan = PathFilters.largerThan(3);
        AbstractPathFilter lessThan = PathFilters.lessThan(10);
        AbstractPathFilter filter = txtFilter.or(bmpFilter).and(largerThan).and(lessThan);

        //Act
        var actual = FileSystemSearcher.searchFiles(tempDir, filter);

        //Assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void searchFilesPathIsFileExceptionTest() throws IOException {
        //Arrange
        createFiles();

        Class<?> expected = IllegalArgumentException.class;
        var pathFile = tempDir.resolve("test1File.txt");
        var filter = PathFilters.READABLE;

        //Act
        Throwable t = org.assertj.core.api.Assertions
            .catchThrowable(() -> FileSystemSearcher.searchFiles(pathFile, filter));

        //Assert
        Assertions.assertEquals(expected, t.getClass());
    }

    @Test
    void searchDirPathMustBeFileExceptionTest() throws IOException {
        //Arrange
        createFiles();
        Class<?> expected = IllegalArgumentException.class;
        var pathFile = tempDir.resolve("test1File.txt");

        //Act
        Throwable t = org.assertj.core.api.Assertions
            .catchThrowable(() -> FileSystemSearcher.searchDirectories(pathFile, 1));

        //Assert
        Assertions.assertEquals(expected, t.getClass());
    }
}
