package edu.project2.generator;

import edu.project2.Cell;

import java.util.HashSet;

public class MazeCellSet extends HashSet<Cell> {
    private int countCellsWithoutBottom;

    public void concatenate(MazeCellSet set) {
        this.addAll(set);
        //this.countCellsWithoutBottom += set.countCellsWithoutBottom;
    }

    public void concatenate(MazeCellSet foundSetRightCell, int countWalledBottoms) {
        int temp = this.countCellsWithoutBottom;
        this.addAll(foundSetRightCell);
        this.countCellsWithoutBottom = temp + countWalledBottoms;
    }

    public boolean isAnyCellWithoutWalledBottomExceptCell(Cell cell) {
        //return !(size()==1||(!cell.isBottomWalled()&&countCellsWithoutBottom==1));

        return (countCellsWithoutBottom > (!cell.isBottomWalled() ? 1 : 0));
    }

    public int getCountCellsWithoutBottom(){
        return countCellsWithoutBottom;
    }

    public void decrementCellsWithoutBottom(){
        countCellsWithoutBottom--;
    }

    @Override
    public boolean remove(Object o) {
        if (!super.remove(o)) {
            return false;
        }
        Cell cell = (Cell) o;
        if (!cell.isBottomWalled()) {
            countCellsWithoutBottom--;
        }
        return true;
    }

    @Override
    public boolean add(Cell cell) {
        if (!super.add(cell)) {
            return false;
        }
        if (!cell.isBottomWalled()) {
            countCellsWithoutBottom++;
        }
        return true;
    }

    public void resetCountCellsWithoutBottom() {
        this.countCellsWithoutBottom=0;
    }


}
