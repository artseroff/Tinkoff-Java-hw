package edu.hw1;

public class Task4 {

    public String fixString(String brokenString) {
        if (brokenString == null) {
            throw new IllegalArgumentException("input string must be not null");
        }
        return brokenString.replaceAll("(.)(.)", "$2$1");
    }
}
