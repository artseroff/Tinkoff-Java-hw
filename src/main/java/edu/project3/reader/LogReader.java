package edu.project3.reader;

import edu.project3.log.Log;
import edu.project3.log.LogRowMapper;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LogReader {

    private static final int HTTP_OK_CODE = 200;

    private LogReader() {
    }

    public static LogsData read(Configuration config) throws IOException {
        List<Log> logs;
        List<String> paths = new ArrayList<>();
        try {
            logs = new ArrayList<>(getLogStream(config, paths)
                .map(LogRowMapper::map)
                .filter(l -> {
                    LocalDateTime start = config.startDate();
                    LocalDateTime end = config.endDate();
                    return (l.date().isAfter(start) || l.date().isEqual(start))
                        && (l.date().isBefore(end) || l.date().isEqual(end));
                })
                .toList());
        } catch (
            URISyntaxException
            | UncheckedIOException
            | IOException
            | InterruptedException e) {
            throw new IOException("Не удалось прочитать логи из файла(-ов)");
        }
        return new LogsData(logs, paths);
    }

    private static Stream<String> getLogStream(Configuration config, List<String> paths)
        throws URISyntaxException, IOException, InterruptedException {
        if (isURL(config.path())) {
            return getLogStreamFromUrl(config.path(), paths);
        } else {
            return getLogStreamFromGlob(config.path(), paths);
        }
    }

    private static boolean isURL(String path) {
        try {
            new URL(path);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }


    public static Stream<String> getLogStreamFromUrl(String url, List<String> paths)
        throws URISyntaxException, IOException, InterruptedException {
        paths.add(url);
        HttpRequest request = HttpRequest
            .newBuilder()
            .uri(new URI(url))
            .GET()
            .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != HTTP_OK_CODE) {
            throw new IOException();
        }
        return response.body().lines();
    }

    public static Stream<String> getLogStreamFromGlob(String pattern, List<String> paths)
        throws IOException {
        List<Path> matches = new ArrayList<>();
        String glob = "glob:" + pattern;
        Path current = Paths.get("");
        FileVisitor<Path> matcherVisitor = new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attribs) {
                PathMatcher matcher = FileSystems.getDefault().getPathMatcher(glob);
                if (matcher.matches(file)) {
                    matches.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        };
        Files.walkFileTree(current, matcherVisitor);

        return matches.stream()
            .peek(p -> paths.add(p.toAbsolutePath().toString()))
            .flatMap(path -> {
                try {
                    return Files.readAllLines(path).stream();
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
    }
}
