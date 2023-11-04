package edu.project2.generator;

import edu.project2.Cell;
import edu.project2.MR;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class Generator {

    private static final MR RANDOM = new MR();

    /**
     * Build maze using eller's method
     *
     * @return maze generated using eller's method
     */
    public static Cell[][] buildMaze(int height, int width) {
        var grid = new Cell[height][width];

        List<MazeCellSet> sets = initSetsWithCellsFromFirstRow(grid[0]);
        for (int i = 0; i < height; i++) {

            initIRow(i, grid, sets);

            for (int j = 0; j < width; j++) {
                final Cell currentCell = grid[i][j];
                MazeCellSet foundSet = findSetByCell(currentCell, sets);

                // нулов быть не должно
                /*if (foundSet == null) {
                    sets.add(createMazeSetFromOneCell(currentCell));
                } else {
                    if (foundSet.contains(grid[i][j+1])) {
                        currentCell.setRightWalled(true);
                    } else {
                        MazeCellSet foundSetRightCell = findSetByCell(grid[i][j+1],sets);
                        // тут и правая ячейка может нигде не быть
                        if (foundSetRightCell == null) {
                            sets.add(createMazeSetFromOneCell(grid[i][j+1]));
                        } else {
                        // тут надо рандом
                            foundSet.concatenate(foundSetRightCell);
                        }
                    }
                }*/
                // right
                if (j != width - 1) {
                    if (foundSet.contains(grid[i][j + 1])) {
                        currentCell.setRightWalled(true);
                    } else {
                        if (RANDOM.nextBoolean()) {
                            currentCell.setRightWalled(true);
                        } else {
                            MazeCellSet foundSetRightCell = findSetByCell(grid[i][j + 1], sets);
                            foundSet.concatenate(foundSetRightCell,getCountWalledBottomsFromIRowInSet(i,grid,foundSetRightCell));
                            sets.remove(foundSetRightCell);
                        }
                    }
                }
            }
                /////////////////AAAAAAAAAAAAAAAAAAAAAAAaaa
                for (int j = 0; j < width; j++) {
                    final Cell currentCell = grid[i][j];
                    MazeCellSet foundSet = findSetByCell(currentCell, sets);

                    //down
                    if (i == height - 1 || (RANDOM.nextBoolean() && foundSet.isAnyCellWithoutWalledBottomExceptCell(currentCell))) {
                        currentCell.setBottomWalled(true);
                        foundSet.decrementCellsWithoutBottom();
                    }


                /*if (i == height - 1){
                    currentCell.setBottomWalled(true);
                }
                if (RANDOM.nextBoolean()) {

                    if (foundSet.isAnyCellWithoutWalledBottomExceptCell(currentCell)){
                        currentCell.setBottomWalled(true);
                    }
                }*/
            }
        }
        for (int j = 0; j < width-1; j++) {
            final Cell currentCell = grid[height-1][j];
            if (currentCell.isRightWalled()) {
                MazeCellSet foundSet = findSetByCell(currentCell, sets);
                if (!foundSet.contains(grid[height-1][j+1])) {
                    MazeCellSet foundSetRightCell = findSetByCell(grid[height-1][j+1], sets);
                    foundSet.concatenate(foundSetRightCell);
                    sets.remove(foundSetRightCell);
                }
            }
        }
        ToStr(grid);
        return grid;
    }

    private static void ToStr(Cell[][] grid) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-".repeat(13)+"\n");
        for (int i = 0; i < grid.length; i++) {

            stringBuilder.append("|");
            for (int j = 0; j < grid[0].length; j++) {
                stringBuilder.append("  ");
                if (grid[i][j].isRightWalled())
                    stringBuilder.append("|");
                else {
                    if (j!=grid[0].length-1)
                        stringBuilder.append(" ");
                }
            }
            stringBuilder.append("|\n");
            stringBuilder.append("-");
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].isBottomWalled())
                    stringBuilder.append("--");
                else {
                    stringBuilder.append("  ");
                }
                if (j!=grid[0].length-1) {
                    stringBuilder.append("-");
                }
            }
            stringBuilder.append("-\n");
        }
        stringBuilder.append("-".repeat(13));
        System.out.println(stringBuilder.toString());
    }

    private static int getCountWalledBottomsFromIRowInSet(int i, Cell[][] grid, MazeCellSet set) {
        int count = 0;
        for (Cell cell : grid[i]) {
            if (set.contains(cell)&& !cell.isBottomWalled()) {
                count++;
            }
        }
        return count;
    }


    private static void initIRow(int i, Cell[][] grid, List<MazeCellSet> sets) {
        if (i == 0) {
            return;
        }
        for (int j = 0; j < grid[i - 1].length; j++) {
            grid[i][j] = new Cell();
            if (grid[i - 1][j].isBottomWalled()) {
                sets.add(createMazeSetFromOneCell(grid[i][j]));
            } else {
                var set = findSetByCell(grid[i - 1][j], sets);
                set.add(grid[i][j]);
            }
        }

        for (MazeCellSet set : sets) {
            set.resetCountCellsWithoutBottom();
        }
    }

    private static List<MazeCellSet> initSetsWithCellsFromFirstRow(Cell[] row) {
        List<MazeCellSet> sets = new LinkedList<>();

        for (int j = 0; j < row.length; j++) {
            row[j] = new Cell();
            sets.add(createMazeSetFromOneCell(row[j]));
        }
        return sets;
    }

    private static MazeCellSet createMazeSetFromOneCell(Cell cell) {
        return new MazeCellSet() {{
            add(cell);
        }};
    }

    private static MazeCellSet findSetByCell(Cell cell, List<MazeCellSet> sets) {

        for (var set : sets) {
            if (set.contains(cell)) {
                return set;
            }
        }
        return null;
    }
}
