package edu.hw8.task1.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class RequestHandler implements Runnable {

    private static final Map<String, String> DIALOGS = Map.of(
        "личности", "Не переходи на личности там, где их нет.",
        "оскорбления", "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами.",
        "глупый", "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
        "интеллект", "Чем ниже интеллект, тем громче оскорбления."
    );

    private static final String DEFAULT_ANSWER = "Принесите пиццы, я не могу работать.";

    private final Socket socket;
    private final BufferedReader clientReader;
    private final PrintStream printStream;

    public RequestHandler(@NotNull Socket socket) throws IOException {
        this.socket = socket;
        this.clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.printStream = new PrintStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                String inputWord = clientReader.readLine();
                if (inputWord == null) {
                    break;
                }
                String answer = DIALOGS.getOrDefault(inputWord, DEFAULT_ANSWER);
                printStream.println(answer);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
