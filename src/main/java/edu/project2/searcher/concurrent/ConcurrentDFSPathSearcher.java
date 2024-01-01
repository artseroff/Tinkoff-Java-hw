package edu.project2.searcher.concurrent;

import edu.project2.Maze;
import edu.project2.searcher.Coordinate;
import edu.project2.searcher.PathSearcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentDFSPathSearcher extends PathSearcher {

    public ConcurrentDFSPathSearcher(Maze maze, Coordinate start, Coordinate end) {
        super(maze, start, end);
    }

    public List<Coordinate> search() {
        path = Collections.synchronizedList(new ArrayList<>());
        Set<Coordinate> visited = Collections.synchronizedSet(new HashSet<>());
        DFSTask task = new DFSTask(maze, path, visited, end, start);
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            forkJoinPool.invoke(task);
        }
        return path;
    }
}
