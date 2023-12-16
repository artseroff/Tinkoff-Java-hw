package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.jetbrains.annotations.NotNull;

public class Client implements Callable<List<String>> {
    private static final String SERVER_DOMAIN = "localhost";
    private final int port;
    private final BufferedReader clientBufferedReader;

    public Client(int port, @NotNull BufferedReader reader) {
        this.clientBufferedReader = reader;
        this.port = port;

    }

    @Override
    public List<String> call() throws IOException {
        List<String> answers = new ArrayList<>();
        try (Socket socket = new Socket(SERVER_DOMAIN, port)) {
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintStream socketOutputStream = new PrintStream(socket.getOutputStream());

            while (!Thread.currentThread().isInterrupted()) {

                String message = clientBufferedReader.readLine();

                if (message == null) {
                    break;
                }
                socketOutputStream.println(message);
                String answer = socketReader.readLine();
                answers.add(answer);

            }
        }
        return answers;
    }
}
