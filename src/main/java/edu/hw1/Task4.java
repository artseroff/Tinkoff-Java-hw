package edu.hw1;

import org.jetbrains.annotations.NotNull;

public class Task4 {

    public String fixString(@NotNull String brokenString) {
        return brokenString.replaceAll("(.)(.)", "$2$1");
    }
}
