package edu.hw7.task3;

import org.jetbrains.annotations.NotNull;

public record Person(int id, @NotNull String name, @NotNull String address, @NotNull String phoneNumber) {
  /*  public Person {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must be not empty");
        }

        if (address.isBlank()) {
            throw new IllegalArgumentException("Name must be not empty");
        }

        if (phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Name must be not empty");
        }
    }*/
}
