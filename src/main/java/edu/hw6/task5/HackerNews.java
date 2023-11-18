package edu.hw6.task5;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HackerNews {
    private static final String TOP_STORIES = "https://hacker-news.firebaseio.com/v0/topstories.json";
    private static final String TOP_STORIES_SEPARATOR = ",";
    private static final String ITEM_INFO_FORMAT = "https://hacker-news.firebaseio.com/v0/item/%d.json";
    private static final int TIMEOUT_DURATION = 20;

    private HackerNews() {

    }

    public static long[] hackerNewsTopStories() throws IOException, InterruptedException, URISyntaxException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(TOP_STORIES))
            .GET()
            .timeout(Duration.of(TIMEOUT_DURATION, ChronoUnit.SECONDS))
            .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            String bodyWithoutBrackets = responseBody.substring(1, responseBody.length() - 1);
            return Arrays.stream(bodyWithoutBrackets.split(TOP_STORIES_SEPARATOR))
                .mapToLong(Long::parseLong)
                .toArray();
        }
    }

    public static String newsTitleById(long id) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI(ITEM_INFO_FORMAT.formatted(id)))
            .GET()
            .timeout(Duration.of(TIMEOUT_DURATION, ChronoUnit.SECONDS))
            .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            return getTitle(responseBody);
        }

    }

    private static String getTitle(String json) {
        String regex = "\"title\"\\s*:\\s*\"([^\"]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
