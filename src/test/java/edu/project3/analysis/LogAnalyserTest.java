package edu.project3.analysis;

import edu.project3.analysis.info.ClientInfo;
import edu.project3.analysis.info.HourInfo;
import edu.project3.analysis.info.ResourceInfo;
import edu.project3.analysis.info.ResponseInfo;
import edu.project3.reader.Configuration;
import edu.project3.reader.IllegalConfigurationException;
import edu.project3.reader.LogReader;
import edu.project3.reader.LogsData;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LogAnalyserTest {
    private static final String TEST_DIRECTORY = "src/test/java/edu/project3";
    private static final Path TEMP_FILE_PATH = Path.of(TEST_DIRECTORY).resolve("reader").resolve("someFile.txt");
    private static final String content = """
        93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 200 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        80.91.33.133 - - [17/May/2015:08:25:24 +0000] "GET /downloads/product_1 HTTP/1.1" 200 1024 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
        217.168.17.5 - - [17/May/2015:09:05:09 +0000] "GET /downloads/product_2 HTTP/1.1" 304 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:10:05:02 +0000] "GET /downloads/product_2 HTTP/1.1" 404 337 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:10:05:42 +0000] "GET /downloads/product_1 HTTP/1.1" 404 332 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
        217.168.17.5 - - [17/May/2015:10:05:12 +0000] "GET /downloads/product_2 HTTP/1.1" 200 3316 "-" "-\"""";

    private void createTempFile() throws IOException {
        Files.writeString(TEMP_FILE_PATH, content);
    }

    @AfterEach
    public void deleteTempFile() throws IOException {
        Files.deleteIfExists(TEMP_FILE_PATH);
    }

    private LogAnalyser buildAnalyser() throws IOException, IllegalConfigurationException {
        String[] args = new String[] {"--path", TEST_DIRECTORY + "/*/*.txt"};
        createTempFile();

        Configuration config = Configuration.parseArgs(args);
        LogsData data = LogReader.read(config);
        return new LogAnalyser(config, data);
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
        Set<ResourceInfo> expectedSet =
            Set.of(new ResourceInfo("/downloads/product_1", 3), new ResourceInfo("/downloads/product_2", 3));

        var analyser = buildAnalyser();
        List<ResourceInfo> resourcesList = analyser.getMostPopularResources(2);
        Set<ResourceInfo> resourcesSet = new HashSet<>(resourcesList);

        Assertions.assertEquals(expectedSet, resourcesSet);

    }

    @Test
    void getMostPopularResponses() throws IllegalConfigurationException, IOException {
        ResponseInfo expected = new ResponseInfo(200, 3);
        var analyser = buildAnalyser();
        ResponseInfo actual = analyser.getMostPopularResponses(1).get(0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getMostActiveClients() throws IllegalConfigurationException, IOException {

        ClientInfo expected = new ClientInfo("217.168.17.5", 4, LocalDateTime.of(2015, 5, 17, 10, 5, 42));
        var analyser = buildAnalyser();
        ClientInfo actual = analyser.getMostActiveClients(1).get(0);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getHoursInfo() throws IllegalConfigurationException, IOException {
        HourInfo expected = new HourInfo(10, 3, 0.5f);
        var analyser = buildAnalyser();
        HourInfo actual = analyser.getHoursInfo().get(10);

        Assertions.assertEquals(expected, actual);
    }
}
