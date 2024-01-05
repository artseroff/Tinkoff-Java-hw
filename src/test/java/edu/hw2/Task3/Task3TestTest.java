package edu.hw2.Task3;

import edu.hw2.Task3.connection.Connection;
import edu.hw2.Task3.connection.FaultyConnection;
import edu.hw2.Task3.connection.StableConnection;
import edu.hw2.Task3.managers.ConnectionManager;
import edu.hw2.Task3.managers.DefaultConnectionManager;
import edu.hw2.Task3.managers.FaultyConnectionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task3TestTest {

    @Test
    public void nullOrEmptyCommandExecute() {
        ConnectionManager manager = new DefaultConnectionManager();
        // try to get stable connection
        Connection conn;
        do {
            conn = manager.getConnection();
        } while (conn instanceof FaultyConnection);
        Connection finalConn = conn;
        Assertions.assertThrows(IllegalArgumentException.class, () -> finalConn.execute(null));
        Assertions.assertThrows(IllegalArgumentException.class, () -> finalConn.execute(""));

    }

    private static Arguments[] commandExecutorInvalidParameters() {
        return new Arguments[] {
            Arguments.of(null, 1),
            Arguments.of(new DefaultConnectionManager(), 0),
            Arguments.of(new FaultyConnectionManager(), -1)
        };
    }

    @ParameterizedTest
    @MethodSource("commandExecutorInvalidParameters")
    public void buildCommandExecutorWithInvalidParameters(ConnectionManager manager, int maxAttempts) {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new PopularCommandExecutor(manager, maxAttempts));
    }

    private static Arguments[] countFaultyConnectionsManagerParameters() {
        return new Arguments[] {
            Arguments.of(new DefaultConnectionManager(), 5, 10),
            Arguments.of(new FaultyConnectionManager(), 10, 10)
        };
    }

    @ParameterizedTest
    @MethodSource("countFaultyConnectionsManagerParameters")
    public void countFaultyConnections(
        ConnectionManager manager,
        int expectedFaultyConnectionsCount,
        int countAttempts
    ) {
        int actualFaultyConnectionsCount = 0;
        for (int i = 0; i < countAttempts; i++) {
            if (manager.getConnection() instanceof FaultyConnection) {
                actualFaultyConnectionsCount++;
            }
        }

        Assertions.assertEquals(expectedFaultyConnectionsCount, actualFaultyConnectionsCount);
    }

    private static Arguments[] connections() {
        return new Arguments[] {
            Arguments.of(new StableConnection(), 0, 10),
            Arguments.of(new FaultyConnection(), 5, 10)
        };
    }

    @ParameterizedTest
    @MethodSource("connections")
    public void countConnectionExceptions(Connection connection, int expectedExceptionsCount, int countAttempts) {
        String command = "javac -x";
        int actualExceptionsCount = 0;
        for (int i = 0; i < countAttempts; i++) {
            try {
                connection.execute(command);
            } catch (ConnectionException e) {
                actualExceptionsCount++;
            }
        }

        Assertions.assertEquals(expectedExceptionsCount, actualExceptionsCount);
    }

    private static Arguments[] managers() {
        return new Arguments[] {
            Arguments.of(new DefaultConnectionManager(), 1, ConnectionException.class, ConnectionException.class),
            Arguments.of(new FaultyConnectionManager(), 1, ConnectionException.class, ConnectionException.class),
            Arguments.of(new DefaultConnectionManager(), 2, null, null),
            Arguments.of(new FaultyConnectionManager(), 2, ConnectionException.class, ConnectionException.class)
        };
    }

    @ParameterizedTest
    @MethodSource("managers")
    public void causesOfExceptionsInTryExecuteTest(
        ConnectionManager manager,
        int countAttempts,
        Class<RuntimeException> expectedExceptionClass,
        Class<RuntimeException> expectedExceptionCause
    ) {
        PopularCommandExecutor executor = new PopularCommandExecutor(manager, countAttempts);
        if (expectedExceptionClass != null) {
            org.assertj.core.api.Assertions.assertThatThrownBy(() -> {
                    executor.updatePackages();
                }).isInstanceOf(expectedExceptionClass)
                .hasCauseInstanceOf(expectedExceptionCause);
        } else {
            org.assertj.core.api.Assertions.assertThatCode(() -> {
                executor.updatePackages();
            }).doesNotThrowAnyException();
        }

    }

}
