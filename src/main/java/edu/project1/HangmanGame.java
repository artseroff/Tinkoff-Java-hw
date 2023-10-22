package edu.project1;

import edu.project1.exceptions.InterruptGameException;
import edu.project1.exceptions.InvalidInputException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import edu.project1.logic.InputController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class HangmanGame {
    private final static Logger LOGGER = LogManager.getLogger();
    private static final String START_MESSAGE = "Загаданное слово имеет %d букв(ы). Осталось %d попыток";
    private static final String SUCCESS_MESSAGE = "Угадали! Слово - %s";
    private static final String FAIL_MESSAGE = "Такой буквы нет. Осталось попыток: %d. Слово - %s";
    public static final int TOTAL_ATTEMPTS = 5;
    /*private final Word word;*/
    private final InputController inputController;
    private int attemptsLeft = TOTAL_ATTEMPTS;

    private HangmanGame(BufferedReader reader) {
        inputController = new InputController(reader);
    }


    public static void start() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in, "windows-1251"))) {
            new HangmanGame(reader).play();
        } catch (InterruptGameException e) {
            LOGGER.info(e.getMessage());
        } catch (IOException e) {
            LOGGER.warn(e.getMessage());
        }
    }

    /*public Game(BufferedReader reader) throws CreateWordException {
        Word word = RandomWordFromFileUtils.getRandomWord();
        this.word = word;
        this.inputController = new InputController(reader, word);
    }

    public void play() throws NeedToStopGameEvent, IOException {
        LOGGER.info(START_MESSAGE.formatted(word.getLength(), attemptsLeft));
        boolean totalWordGuessed = false;
        while (attemptsLeft > 0 && !totalWordGuessed) {
            Boolean guessResult = null;
            while (guessResult == null) {
                try {
                    LOGGER.info("Введите букву:");
                    guessResult = inputController.proccessGuess();
                } catch (WrongInputException e) {
                    LOGGER.warn(e.getMessage());
                }
            }
            if (guessResult) {
                LOGGER.info(SUCCESS_MESSAGE.formatted(word.getView()));
                totalWordGuessed = word.isAllLettersGuessed();
            } else {
                LOGGER.info(FAIL_MESSAGE.formatted(--attemptsLeft, word.getView()));
            }
        }
        if (totalWordGuessed) {
            LOGGER.info("Вы выиграли!");
        } else {
            LOGGER.info("Вы проиграли, попытки закончились");
        }
    }*/

    private void play() throws InterruptGameException, IOException {
        boolean wordGuessed = false;
        // тут будет состояние
        while (attemptsLeft > 0 && !wordGuessed) {
            char ch = tryGetValidCharForGuessing();
            // session.getMove(char буква);
            // session.getMessage
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
