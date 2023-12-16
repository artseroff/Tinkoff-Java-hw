package edu.hw8.task1;

import edu.hw8.task1.server.NetworkService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NetworkServiceTest {

    private int getFreePort() throws IOException {
        try (var socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        }
    }

    @Test
    public void responseTest() throws InterruptedException, IOException {
        int port = getFreePort();

        List<List<String>> expected = List.of(
            List.of(
                "Не переходи на личности там, где их нет.",
                "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами."
            ),
            List.of("А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма."),
            List.of(),
            List.of("Принесите пиццы, я не могу работать.", "Принесите пиццы, я не могу работать.")
        );

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        NetworkService networkService = new NetworkService(2, port);

        List<String> requestsList = List.of("личности\nоскорбления", "глупый", "", "любое\nслово");
        List<Client> clientsList = new ArrayList<>();
        for (String request : requestsList) {
            clientsList.add(new Client(port, new BufferedReader(new StringReader(request))));
        }
        try {
            networkService.start();

            var answers = executorService.invokeAll(clientsList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
            Assertions.assertEquals(expected,answers);

        } finally {
            executorService.close();
        }
    }

    @Test
    public void highloadTest() throws InterruptedException, IOException {
        int port = getFreePort();

        List<String> requestsList = List.of(" \n".repeat(500).split("\n"));

        List<String> expected = List.of("Принесите пиццы, я не могу работать.");

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        NetworkService networkService = new NetworkService(2, port);

        List<Client> clientsList = new ArrayList<>();
        for (String request : requestsList) {
            clientsList.add(new Client(port, new BufferedReader(new StringReader(request))));
        }
        try {
            networkService.start();

            var answers = executorService.invokeAll(clientsList).stream()
                .map(future -> {
                    try {
                        return future.get(1, TimeUnit.NANOSECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
            for (int i = 0; i < expected.size(); i++) {
                Assertions.assertEquals(expected, answers.get(i));
            }

        } finally {
            executorService.close();
        }
    }
}
