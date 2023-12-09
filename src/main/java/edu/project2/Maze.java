package edu.project2;

import edu.project2.searcher.Coordinate;
import java.util.LinkedList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

public record Maze(int rows, int cols, @NotNull Cell[][] grid) {

    public List<Coordinate> getPossiblePassages(Coordinate coordinate) {
        int i = coordinate.row();
        int j = coordinate.col();

        List<Coordinate> list = new LinkedList<>();
        if (j < cols && !grid[i][j].isRightWalled()) {
            list.add(new Coordinate(i, j + 1));
        }
        if (i < rows && !grid[i][j].isBottomWalled()) {
            list.add(new Coordinate(i + 1, j));
        }
        if (j > 0 && !grid[i][j - 1].isRightWalled()) {
            list.add(new Coordinate(i, j - 1));
        }
        if (i > 0 && !grid[i - 1][j].isBottomWalled()) {
            list.add(new Coordinate(i - 1, j));
        }
        return list;
    }
}
