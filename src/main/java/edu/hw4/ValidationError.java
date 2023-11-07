package edu.hw4;

public record ValidationError(String field, String text) {
    @Override public String toString() {
        return "%s %s".formatted(field, text);
    }
}
