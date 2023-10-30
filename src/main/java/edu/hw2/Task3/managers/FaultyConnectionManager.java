package edu.hw2.Task3.managers;

import edu.hw2.Task3.connection.Connection;
import edu.hw2.Task3.connection.FaultyConnection;

public class FaultyConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() {
        return new FaultyConnection();
    }
}
