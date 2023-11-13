package edu.hw5.task4;

import org.jetbrains.annotations.NotNull;

public class Task4 {
    public boolean isValidPassword(@NotNull String password) {
        return password.matches(".*[~!@#$%^&*|].*");
    }

}
