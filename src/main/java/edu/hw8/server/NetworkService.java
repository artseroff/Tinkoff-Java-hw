package edu.hw8.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NetworkService extends Thread {

    private final int maxCountClients;

    private final int port;

    public NetworkService(int maxCountClients, int port) {
        this.maxCountClients = maxCountClients;
        this.port = port;
    }

    @Override
    public void run() {
        try (ServerSocket socket = new ServerSocket(port);
             ExecutorService executorService = Executors.newFixedThreadPool(maxCountClients)) {

            socket.setReuseAddress(true);

            while (!isInterrupted()) {

                Socket accepted = socket.accept();
                var handler = new RequestHandler(accepted);
                executorService.execute(handler);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
