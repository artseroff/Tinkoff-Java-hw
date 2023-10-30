package edu.hw2.Task3.managers;

import edu.hw2.Task3.connection.Connection;
import edu.hw2.Task3.connection.FaultyConnection;
import edu.hw2.Task3.connection.StableConnection;

public class DefaultConnectionManager implements ConnectionManager {

    // FaultyConnection will be returned for every even call getConnection
    private boolean isEvenCallGetConnection = true;

    @Override
    public Connection getConnection() {
        Connection conn;
        if (isEvenCallGetConnection) {
            conn = new FaultyConnection();
        } else {
            conn = new StableConnection();
        }
        isEvenCallGetConnection = !isEvenCallGetConnection;
        return conn;
    }
}
