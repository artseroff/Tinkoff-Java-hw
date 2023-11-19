package edu.project3.analysis;

import edu.project3.reader.Configuration;
import edu.project3.reader.IllegalConfigurationException;
import edu.project3.reader.LogReader;
import edu.project3.reader.LogsData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogAnalyserTest {
    private static final String TEST_DIRECTORY = "src/test/java/edu/project3";
    private static final Path TEMP_FILE_PATH = Path.of(TEST_DIRECTORY).resolve("reader").resolve("someFile.txt");
    private static final String content = """
        93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        80.91.33.133 - - [17/May/2015:08:05:24 +0000] "GET /downloads/product_1 HTTP/1.1" 304 1024 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
        217.168.17.5 - - [17/May/2015:08:05:09 +0000] "GET /downloads/product_2 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:08:05:02 +0000] "GET /downloads/product_2 HTTP/1.1" 404 337 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:08:05:42 +0000] "GET /downloads/product_1 HTTP/1.1" 404 332 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:08:05:12 +0000] "GET /downloads/product_2 HTTP/1.1" 200 3316 "-" "-\"""";

    private void createTempFile() throws IOException {
        Files.writeString(TEMP_FILE_PATH, content);
    }

    @AfterEach
    private void deleteTempFile() throws IOException {
        Files.deleteIfExists(TEMP_FILE_PATH);
    }

    private LogAnalyser buildAnalyser() throws IOException, IllegalConfigurationException {
        String[] args = new String[] {"--path", TEST_DIRECTORY + "/*/*.txt"};
        createTempFile();

        Configuration config = Configuration.parseArgs(args);
        LogsData data = LogReader.read(config);
        LogAnalyser analyser = new LogAnalyser(config, data);
        return analyser;
    }

    @Test
    void getCommonInfo() throws IOException, IllegalConfigurationException {
        String expectedCountRequests = "6";
        String expectedAverageSize = "916b";
        var analyser = buildAnalyser();
        var infosList = analyser.getCommonInfo();

        Assertions.assertEquals(expectedCountRequests, infosList.get(3).value());
        Assertions.assertEquals(expectedAverageSize, infosList.get(4).value());

    }

    @Test
    void getMostPopularResources() throws IllegalConfigurationException, IOException {

    }

    @Test
    void getMostPopularResponses() {
    }

    @Test
    void getMostActiveClients() {
    }

    @Test
    void getHoursInfo() {
    }
}
