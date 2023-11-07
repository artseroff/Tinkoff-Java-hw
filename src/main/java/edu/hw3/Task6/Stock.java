package edu.hw3.Task6;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public record Stock(@NotNull UUID uuid, @NotNull String name, @NotNull Double price) {
    public Stock {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must be not blank");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
    }

    public Stock(UUID uuid, Double price) {
        this(uuid, "stock_" + uuid, price);
    }
}
