package edu.project1;

import edu.project1.exceptions.InvalidWordException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DictionaryUtils {
    private final static List<String> WORDS = new ArrayList<>();

    static {
        initWords();
    }

    public static void initWords() {
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classloader.getResourceAsStream("words.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                WORDS.add(line);
            }

        } catch (IOException | NullPointerException e) {
            throw new InvalidWordException("Не удалось инициализировать словарь загадываемых слов");
        }
    }

    private DictionaryUtils() {
    }

    public static String randomWord() {
        int wordsSize = WORDS.size();
        int randomWordIndex = (int) (Math.random() * wordsSize);
        return WORDS.get(randomWordIndex).trim().toLowerCase();
    }

}
