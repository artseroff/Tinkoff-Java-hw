package edu.project1.logic;

import edu.project1.exceptions.InterruptGameException;
import edu.project1.exceptions.InvalidInputException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class InputControllerTest {

    @Test
    public void validateGuess() {
    }
    @Test
    public void interruptGame() {
        String input = "";
        BufferedReader reader = new BufferedReader(new StringReader(input));
        InputController controller = new InputController(reader);
        Assertions.assertThrows(InterruptGameException.class, controller::validateGuess);
    }

    @Test
    public void emptyInput() {
        String input = " ";
        BufferedReader reader = new BufferedReader(new StringReader(input));
        InputController controller = new InputController(reader);
        Assertions.assertThrows(InvalidInputException.class, controller::validateGuess);
    }

    @Test
    public void manyCharsInput() {
        String input = "абакаба";
        BufferedReader reader = new BufferedReader(new StringReader(input));
        InputController controller = new InputController(reader);
        Assertions.assertThrows(InvalidInputException.class, controller::validateGuess);
    }

    @Test
    public void englishCharInput() {
        String input = "h";
        BufferedReader reader = new BufferedReader(new StringReader(input));
        InputController controller = new InputController(reader);
        Assertions.assertThrows(InvalidInputException.class, controller::validateGuess);
    }

    @Test
    public void repeatedCharInput() throws InvalidInputException, InterruptGameException, IOException {
        String input = "а\nа";
        BufferedReader reader = new BufferedReader(new StringReader(input));
        InputController controller = new InputController(reader);
        controller.validateGuess();
        Assertions.assertThrows(InvalidInputException.class, controller::validateGuess);
    }
}
