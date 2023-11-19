package edu.project3.reader;

import edu.hw4.Animal;
import edu.hw4.AnimalUtils;
import edu.project3.log.Log;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LogReaderTest {
    private static final String TEST_DIRECTORY = "src/test/java/edu/project3";
    private static final Path TEMP_FILE_PATH = Path.of(TEST_DIRECTORY).resolve("reader").resolve("someFile.txt");
    private static final String content = """
        93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        93.180.71.3 - - [17/May/2015:08:05:23 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        80.91.33.133 - - [17/May/2015:08:05:24 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
        217.168.17.5 - - [17/May/2015:08:05:34 +0000] "GET /downloads/product_1 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:08:05:09 +0000] "GET /downloads/product_2 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        93.180.71.3 - - [17/May/2015:08:05:57 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        217.168.17.5 - - [17/May/2015:08:05:02 +0000] "GET /downloads/product_2 HTTP/1.1" 404 337 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:08:05:42 +0000] "GET /downloads/product_1 HTTP/1.1" 404 332 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        80.91.33.133 - - [17/May/2015:08:05:01 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
        93.180.71.3 - - [17/May/2015:08:05:27 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        217.168.17.5 - - [17/May/2015:08:05:12 +0000] "GET /downloads/product_2 HTTP/1.1" 200 3316 "-" "-\"""";

    private void createTempFile() throws IOException {
        Files.writeString(TEMP_FILE_PATH, content);
    }

    private void deleteTempFile() throws IOException {
        Files.delete(TEMP_FILE_PATH);
    }



    private static Arguments[] readFilesParam() {
        return new Arguments[] {
            Arguments.of((Object) new String[]{"--path", TEST_DIRECTORY + "/*/*.txt"}),
            /*Arguments.of((Object) new String[] {"--path",
                "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs"})

        */};
    }

    @ParameterizedTest
    @MethodSource("readFilesParam")
    public void readFiles(String[] args) throws IllegalConfigurationException, IOException {
        Log expectedFirstLog = new Log(
            "93.180.71.3",
            "-",
            LocalDateTime.of(2015, 5, 17, 8, 5, 32),
            "GET",
            "/downloads/product_1",
            "HTTP/1.1",
            304,
            0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)",
            null
        );

        createTempFile();

        Configuration config = Configuration.parseArgs(args);
        LogsData data = LogReader.read(config);

        Assertions.assertEquals(expectedFirstLog, data.logs().get(0));

        deleteTempFile();

    }

}
