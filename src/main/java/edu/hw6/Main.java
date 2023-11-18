package edu.hw6;

import edu.hw6.task4.OutputStreamComposition;
import java.io.IOException;
import java.nio.file.Path;

public class Main {

    private Main() {

    }

    public static void main(String[] args) throws IOException {
        OutputStreamComposition.write(Path.of("src/test/java/edu/hw6/task4").resolve("1.txt"), "abebra");
    }
}
