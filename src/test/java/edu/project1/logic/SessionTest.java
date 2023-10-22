package edu.project1.logic;

import edu.project1.logic.utils.WordGuessingTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

public class SessionTest {

    @Test
    public void testStateWin() {
        State expected = State.WIN;

        Session session = new Session(5);
        GuessedWord guessedWord = session.getWord();

        char[] chars = guessedWord.getStringWord().toCharArray();
        for (char ch: chars) {
            session.nextMove(ch);
        }

        Assertions.assertEquals(expected, session.getState());
    }

    @Test
    public void testStateSuccessfulGuessChar() {
        State expected = State.SUCCESSFUL_GUESS;

        Session session = new Session(5);
        GuessedWord guessedWord = session.getWord();

        char ch = guessedWord.getStringWord().toCharArray()[0];
        session.nextMove(ch);

        Assertions.assertEquals(expected, session.getState());
    }

    @Test
    public void testStateDefeat() {
        State expected = State.DEFEAT;

        Session session = new Session(1);
        GuessedWord guessedWord = session.getWord();

        char ch = WordGuessingTestUtils.getLetterNotInWord(guessedWord.getStringWord());
        for (int i=0; i<guessedWord.getLength(); i++) {
            session.nextMove(ch);
        }

        Assertions.assertEquals(expected, session.getState());
    }

    @Test
    public void testStateFailedGuess() {
        State expected = State.FAILED_GUESS;

        Session session = new Session(5);
        GuessedWord guessedWord = session.getWord();

        char ch = WordGuessingTestUtils.getLetterNotInWord(guessedWord.getStringWord());
        session.nextMove(ch);

        Assertions.assertEquals(expected, session.getState());
    }


    /*@SuppressWarnings("MagicNumber")
    private static Arguments[] provideWordsAndChars() {
        return new Arguments[] {
            Arguments.of(new char[]{'п'}, State.SUCCESSFUL_GUESS),
            Arguments.of(new char[]{'е'}, State.FAILED_GUESS),
            Arguments.of("слоАво".toCharArray(), State.WIN),
            Arguments.of("опять".toCharArray(), State.DEFEAT)
        };
    }*/
}
