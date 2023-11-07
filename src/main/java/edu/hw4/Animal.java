package edu.hw4;

public record Animal(
    String name,
    Type type,
    Sex sex,
    /**years**/
    int age,
    /**in mm**/
    int height,
    /**in gramms**/
    int weight,
    boolean bites
) {
    enum Type {
        CAT, DOG, BIRD, FISH, SPIDER
    }

    enum Sex {
        M, F
    }

    @SuppressWarnings("MagicNumber")
    public int paws() {
        return switch (type) {
            case CAT, DOG -> 4;
            case BIRD -> 2;
            case FISH -> 0;
            case SPIDER -> 8;
        };
    }
}
