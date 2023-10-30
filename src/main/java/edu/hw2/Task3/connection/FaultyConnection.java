package edu.hw2.Task3.connection;

import edu.hw2.Task3.ConnectionException;

public class FaultyConnection implements Connection {

    // An exception will be thrown for every even connection
    private boolean isEvenConnection = true;

    @Override
    public void execute(String command) {
        try {
            if (isEvenConnection) {
                throw new ConnectionException("FaultyConnection is not working");
            }
            Connection.super.execute(command);
        } finally {
            isEvenConnection = !isEvenConnection;
        }

    }
}
