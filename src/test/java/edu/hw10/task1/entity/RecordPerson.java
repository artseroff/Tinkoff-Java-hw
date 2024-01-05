package edu.hw10.task1.entity;

import edu.hw10.task1.annotations.Max;
import edu.hw10.task1.annotations.Min;
import edu.hw10.task1.annotations.NotNull;

public record RecordPerson(@Min(0) @Max(150) int age,
                           @NotNull String firstname,
                           @NotNull String surname,
                           String patronymic) {
}
