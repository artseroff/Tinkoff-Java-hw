package edu.project1;

import edu.project1.exceptions.InterruptGameException;
import edu.project1.exceptions.InvalidInputException;
import edu.project1.logic.InputController;
import edu.project1.logic.Session;
import edu.project1.logic.State;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class HangmanGame {
    private final static Logger LOGGER = LogManager.getLogger();
    public static final int MAX_ATTEMPTS = 5;
    private final InputController inputController;

    private HangmanGame(BufferedReader reader) {
        inputController = new InputController(reader);
    }

    public static void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            new HangmanGame(reader).play();
        } catch (InterruptGameException e) {
            LOGGER.info(e.getMessage());
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    private void play() throws InterruptGameException, IOException {
        Session session = new Session(MAX_ATTEMPTS);
        LOGGER.info("Загаданное слово состоит из %d букв. Осталось %d попыток".formatted(
            session.getWord().getLength(),
            session.getAttemptsLeft()
        ));
        while (!session.getState().equals(State.WIN) && !session.getState().equals(State.DEFEAT)) {
            char ch = tryGetValidCharForGuessing();
            session.nextMove(ch);
            LOGGER.info(session.getMessage());
        }
    }

    private char tryGetValidCharForGuessing() throws InterruptGameException, IOException {
        char input;
        do {
            try {
                LOGGER.info("Введите букву:");
                input = inputController.validateGuess();
                break;
            } catch (InvalidInputException e) {
                LOGGER.warn(e.getMessage());
            }
        } while (true);
        return input;
    }
}
