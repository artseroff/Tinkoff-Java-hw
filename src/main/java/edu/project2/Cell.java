package edu.project2;

public class Cell {
    private boolean isRightWalled;
    private boolean isBottomWalled;

    public Cell() {
    }

    public static Cell buildCopyOfBottom(Cell cell) {
        Cell copy = new Cell();
        copy.isBottomWalled = cell.isBottomWalled;
        return copy;
    }

    public boolean isRightWalled() {
        return isRightWalled;
    }

    public void setRightWalled(boolean rightWalled) {
        isRightWalled = rightWalled;
    }

    public boolean isBottomWalled() {
        return isBottomWalled;
    }

    public void setBottomWalled(boolean bottomWalled) {
        isBottomWalled = bottomWalled;
    }

}
