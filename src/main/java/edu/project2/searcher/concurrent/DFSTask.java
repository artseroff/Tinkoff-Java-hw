package edu.project2.searcher.concurrent;

import edu.project2.Maze;
import edu.project2.searcher.Coordinate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DFSTask extends RecursiveTask<Boolean> {
    private final Maze maze;
    private final List<Coordinate> path;
    private final Set<Coordinate> visited;
    private final Coordinate end;
    private final Coordinate coordinate;

    public DFSTask(Maze maze, List<Coordinate> path, Set<Coordinate> visited, Coordinate end, Coordinate coordinate) {
        this.maze = maze;
        this.path = path;
        this.visited = visited;
        this.end = end;
        this.coordinate = coordinate;
    }

    @Override
    protected Boolean compute() {
        visited.add(coordinate);
        if (coordinate.equals(end)) {
            path.addFirst(coordinate);
            return true;
        }
        List<DFSTask> tasks = new ArrayList<>();
        for (Coordinate neighbour : maze.getPossiblePassages(coordinate)) {
            if (!visited.contains(neighbour)) {
                DFSTask task = new DFSTask(maze, path, visited, end, neighbour);
                tasks.add(task);
                task.fork();
            }
        }
        for (DFSTask task : tasks) {
            if (task.join()) {
                path.addFirst(coordinate);
                return true;
            }
        }
        return false;
    }
}
