package edu.project1.logic;

import edu.project1.exceptions.InterruptGameException;
import edu.project1.exceptions.InvalidInputException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class InputController {
    private final BufferedReader reader;
    private Set<Character> inputtedLetters = new HashSet<>();

    public InputController(BufferedReader reader) {
        this.reader = reader;
    }

    public char validateGuess() throws InterruptGameException, InvalidInputException, IOException {
        String input = null;
        input = reader.readLine();
        if (input == null) {
            throw new InterruptGameException("Игра прервана");
        }
        input = input.trim().toLowerCase();
        if ("".equals(input) || (input.length() > 1) || !input.matches("[а-я]")) {
            throw new InvalidInputException("Неверный ввод. Введите только 1 букву русского алфавита");
        }
        char symbol = input.charAt(0);
        if (inputtedLetters.contains(symbol)) {
            throw new InvalidInputException("Неверный ввод. Вы уже вводили эту букву раньше");
        }
        inputtedLetters.add(symbol);
        return symbol;
    }
}
