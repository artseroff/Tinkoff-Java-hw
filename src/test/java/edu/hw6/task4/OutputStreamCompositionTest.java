package edu.hw6.task4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OutputStreamCompositionTest {

    @Test
    public void write() throws IOException {
        Path path = Path.of("src/test/java/edu/hw6/task4").resolve("1.txt");
        String textInsideFile = "textInsideFile";
        OutputStreamComposition.write(path, textInsideFile);
        Assertions.assertEquals(textInsideFile, Files.readString(path));
    }
}
