package edu.project2.searcher;

import edu.project2.Cell;
import edu.project2.Maze;
import edu.project2.generator.DFSMazeGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PathSearcherTest {

    private static int N1;
    private static int M1;
    private static Maze normalMaze;
    private static Maze mazeWithNoPaths;

    @BeforeAll
    static void initialize() {
        N1 = 15;
        M1 = 40;
        normalMaze = new DFSMazeGenerator(N1,M1).generate();
        mazeWithNoPaths = new Maze(1, 2, new Cell[][] {{new Cell(true,true),new Cell(true,true)}});
    }

    @Test
    void dfsSearchNormalMaze() {
        for (int i = 0; i < 100; i++) {
            Coordinate start = Coordinate.getRandom(N1, M1);
            Coordinate end = Coordinate.getRandom(N1, M1);
            Assertions.assertFalse(new DFSPathSearcher(normalMaze,start,end).search().isEmpty());
        }
    }

    @Test
    void bfsSearchNormalMaze() {
        for (int i = 0; i < 100; i++) {
            Coordinate start = Coordinate.getRandom(N1, M1);
            Coordinate end = Coordinate.getRandom(N1, M1);
            Assertions.assertFalse(new BFSPathSearcher(normalMaze,start,end).search().isEmpty());
        }
    }

    @Test
    void dfsSearchNoPaths() {
        for (int i = 0; i < 100; i++) {
            Coordinate start = new Coordinate(0,0);
            Coordinate end = new Coordinate(0,1);
            Assertions.assertTrue(new DFSPathSearcher(mazeWithNoPaths,start,end).search().isEmpty());
        }
    }

    @Test
    void bfsSearchNoPaths() {
        for (int i = 0; i < 100; i++) {
            Coordinate start = new Coordinate(0,0);
            Coordinate end = new Coordinate(0,1);
            Assertions.assertTrue(new BFSPathSearcher(mazeWithNoPaths,start,end).search().isEmpty());
        }
    }

    static Arguments[] searchers() {
        Maze maze = new Maze(2,2, new Cell[][] {{new Cell(),new Cell()},{new Cell(),new Cell()}});
        var start = new Coordinate(0,0);
        var end = new Coordinate(1,1);
        return new Arguments[]{
            Arguments.of(new BFSPathSearcher(maze,start,end), new Coordinate(0,0), Set.of(new Coordinate(0,1),new Coordinate(1,0))),
            Arguments.of(new DFSPathSearcher(maze,start,end), new Coordinate(0,0), Set.of(new Coordinate(0,1),new Coordinate(1,0)))
        };
    }

    @ParameterizedTest
    @MethodSource("searchers")
    public void getPossiblePassages(PathSearcher searcher, Coordinate coordinate, Set<Coordinate> expected) {
        var actual = new HashSet<>(searcher.getPossiblePassages(coordinate));
        Assertions.assertEquals(expected,actual);
    }
}
