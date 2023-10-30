package edu.hw2.Task3;

import edu.hw2.Task3.connection.Connection;
import edu.hw2.Task3.managers.ConnectionManager;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        if (manager == null) {
            throw new IllegalArgumentException("Connection manager must be not null");
        }
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maximum number of attempts must be > 0");
        }
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    void tryExecute(String command) {
        for (int i = 0; i < maxAttempts; i++) {
            try (Connection conn = manager.getConnection()) {
                conn.execute(command);
                break;
            } catch (ConnectionException e) {
                if (i == maxAttempts - 1) {
                    throw new ConnectionException(("Exceeded the maximum "
                        + "number of attempts to execute command '%s'").formatted(command), e);
                }
            }
        }
    }
}
