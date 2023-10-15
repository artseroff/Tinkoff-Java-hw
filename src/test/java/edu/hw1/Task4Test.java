package edu.hw1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class Task4Test {

    private static final Task4 TASK_4 = new Task4();

    @ParameterizedTest
    @CsvSource(value = {
        "123456, 214365",
        "12345, 21435",
        "hTsii  s aimex dpus rtni.g, This is a mixed up string.",
        "оПомигети псаривьтс ртко!и, Помогите исправить строки!"
    })
    public void fixStringProperInput(String brokenString, String expected) {
        Assertions.assertEquals(expected, TASK_4.fixString(brokenString));
    }

    @Test
    public void fixStringNullInput() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> TASK_4.fixString(null));

    }
}
