package edu.project2.searcher;

import edu.project2.Maze;
import java.util.ArrayList;
import java.util.List;

public abstract class PathSearcher {
    protected Maze maze;
    protected List<Coordinate> path = new ArrayList<>();
    protected Coordinate start;
    protected Coordinate end;

    public PathSearcher(Maze maze, Coordinate start, Coordinate end) {
        if (start.row() >= maze.rows() || start.col() >= maze.cols()) {
            throw new IllegalArgumentException("Начальная клетка выходит за границы лабиринта");
        }
        if (end.row() >= maze.rows() || end.col() >= maze.cols()) {
            throw new IllegalArgumentException("Конечная клетка выходит за границы лабиринта");
        }
        this.maze = maze;
        this.start = start;
        this.end = end;
    }

    public abstract List<Coordinate> search();

}
