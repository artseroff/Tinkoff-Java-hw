package edu.project1.logic;

import edu.project1.DictionaryUtils;

public class Session {

    private static final String SUCCESS_MESSAGE = "Угадали! Слово - %s";

    private static final String WIN_MESSAGE = SUCCESS_MESSAGE + "\nПобеда!";

    private static final String FAIL_MESSAGE = "Такой буквы нет. Осталось попыток: %d. Слово - %s";

    private static final String DEFEAT_MESSAGE = FAIL_MESSAGE
        + "\nПроигрыш, попытки закончились! Загаданное слово - %s";

    private static final String NONE_MESSAGE = "Начало игры";

    private GuessedWord word;

    private State state;

    private int attemptsLeft;

    public Session(int maxAttempts) {
        state = State.NONE;
        word = new GuessedWord(DictionaryUtils.randomWord());
        attemptsLeft = maxAttempts;
    }

    public void nextMove(char ch) {
        boolean result = word.guessChar(ch);
        if (result) {
            if (word.isWordGuessed()) {
                state = State.WIN;
            } else {
                state = State.SUCCESSFUL_GUESS;
            }
        } else {
            state = State.FAILED_GUESS;
            attemptsLeft--;
            if (attemptsLeft <= 0) {
                state = State.DEFEAT;
            }
        }
    }

    public State getState() {
        return state;
    }

    public GuessedWord getWord() {
        return word;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getMessage() {
        return switch (state) {
            case SUCCESSFUL_GUESS -> SUCCESS_MESSAGE.formatted(word.encryptedView());
            case WIN -> WIN_MESSAGE.formatted(word.encryptedView());
            case FAILED_GUESS -> FAIL_MESSAGE.formatted(attemptsLeft, word.encryptedView());
            case DEFEAT -> DEFEAT_MESSAGE.formatted(attemptsLeft, word.encryptedView(), word.getStringWord());
            case NONE -> NONE_MESSAGE;
        };
    }
}
