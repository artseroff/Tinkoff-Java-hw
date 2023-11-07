package edu.project2;

import org.jetbrains.annotations.NotNull;

public record Maze(int rows, int cols, @NotNull Cell[][] grid) {
}
