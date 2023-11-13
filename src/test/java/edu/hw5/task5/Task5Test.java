package edu.hw5.task5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Task5Test {
    private static final Task5 TASK_5 = new Task5();

    @ParameterizedTest
    @ValueSource(strings = {"А111АА777", "А111АА10", "А111АА01", "А111АА101", "А111АА110", "А001МР777", "В200ОР777",
        "Е030КХ23"}) public void validRussianCarNumberTest(String number) {
        Assertions.assertTrue(TASK_5.isRussianCarNumber(number));
    }

    @ParameterizedTest
    @ValueSource(strings = {"АБВ111АА62", "Б111МВ777", "А111ААБМ", "Г111ДЁ777", "а111аа777", "А000АА11", "А111АА7777",
        "АА111А777", "А1111АА777", "А111АА00", "А111АА001", "А111АА062", "А111АА000", "А111АА100", "А111АА001",
        "А111АА000", " ", ""}) public void invalidRussianCarNumberTest(String number) {
        Assertions.assertFalse(TASK_5.isRussianCarNumber(number));
    }
}
