package edu.hw6.task4;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

public class OutputStreamComposition {
    private OutputStreamComposition() {
    }

    public static void write(Path path, String text) throws IOException {

        try (OutputStream outputStream = Files.newOutputStream(path, StandardOpenOption.CREATE);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(outputStream, new Adler32());
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter =
                 new OutputStreamWriter(bufferedOutputStream, StandardCharsets.UTF_8);

             PrintWriter writer = new PrintWriter(outputStreamWriter)) {
            writer.print(text);
        }
    }
}
