package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FileClonerTest {

    @Test
    public void cloneNonExistingFile() {
        Assertions.assertThrows(NoSuchFileException.class, () -> FileCloner.cloneFile(Path.of("abacaba.txt")));
    }

    @Test
    public void cloneDirectory() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> FileCloner.cloneFile(Path.of("src/test")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", ".txt"})
    public void cloneFile(String extension) throws IOException {
        Path file = Path.of("src/test/java/edu/hw6/task2/files/TestFile" + extension);
        FileCloner.cloneFile(file);
        String firstCloneName = "TestFile - копия"+extension;
        Path firstClonePath = file.resolveSibling(firstCloneName);
        Assertions.assertTrue(Files.exists(firstClonePath));
        Assertions.assertArrayEquals(Files.readAllBytes(file),Files.readAllBytes(firstClonePath));

        FileCloner.cloneFile(file);
        String secondCloneName = "TestFile - копия (2)"+extension;
        Path secondClonePath = file.resolveSibling(secondCloneName);
        Assertions.assertTrue(Files.exists(secondClonePath));
        Assertions.assertArrayEquals(Files.readAllBytes(file),Files.readAllBytes(secondClonePath));

        FileCloner.cloneFile(file);
        String thirdCloneName = "TestFile - копия (3)"+extension;
        Path thirdClonePath = file.resolveSibling(thirdCloneName);
        Assertions.assertTrue(Files.exists(thirdClonePath));
        Assertions.assertArrayEquals(Files.readAllBytes(file),Files.readAllBytes(thirdClonePath));

        Files.delete(firstClonePath);
        FileCloner.cloneFile(file);
        Assertions.assertTrue(Files.exists(firstClonePath));
        Assertions.assertArrayEquals(Files.readAllBytes(file),Files.readAllBytes(firstClonePath));

        Files.delete(firstClonePath);
        Files.delete(secondClonePath);
        Files.delete(thirdClonePath);
    }
}
