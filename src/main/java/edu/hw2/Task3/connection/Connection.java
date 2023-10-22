package edu.hw2.Task3.connection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface Connection extends AutoCloseable {
    Logger LOGGER = LogManager.getLogger();

    default void execute(String command) {
        if (command == null) {
            throw new IllegalArgumentException("Command must be not null");
        }
        String trimmedCommand = command.trim();
        if (trimmedCommand.isEmpty()) {
            throw new IllegalArgumentException("Command must be not empty");
        }
        LOGGER.info("The command '%s' was executed successfully".formatted(command));
    }

    default void close() {
        LOGGER.info("%s closed".formatted(getClass().getSimpleName()));
    }
}
