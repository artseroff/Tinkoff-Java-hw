package edu.project2.searcher;

import edu.project2.Maze;
import java.util.List;

public class DFSPathSearcher extends PathSearcher {

    public DFSPathSearcher(Maze maze, Coordinate start, Coordinate end) {
        super(maze, start, end);
    }

    @Override
    public List<Coordinate> search() {
        dfs(start);
        return path;
    }

    private boolean dfs(Coordinate coordinate) {
        if (coordinate.equals(end)) {
            path.add(coordinate);
            return true;
        }
        path.add(coordinate);
        for (Coordinate neighbour : maze.getPossiblePassages(coordinate)) {
            if (!path.contains(neighbour)) {
                if (dfs(neighbour)) {
                    return true;
                }
            }
        }
        path.removeLast();
        return false;
    }
}
