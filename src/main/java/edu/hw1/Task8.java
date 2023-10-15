package edu.hw1;

public class Task8 {
    private static final int FIELD_SIZE = 8;

    private static final String INVALID_FIELD_SIZE_TEXT = "Field size is not 8x8";

    public boolean knightBoardCapture(int[][] field) {
        if (field == null) {
            throw new IllegalArgumentException("field must be not null");
        }
        if (field.length != FIELD_SIZE) {
            throw new IllegalArgumentException(INVALID_FIELD_SIZE_TEXT);
        }
        for (int[] line : field) {
            if (line.length != FIELD_SIZE) {
                throw new IllegalArgumentException(INVALID_FIELD_SIZE_TEXT);
            }
        }
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j] != 0 && field[i][j] != 1) {
                    throw new IllegalArgumentException(INVALID_FIELD_SIZE_TEXT);
                }
                int localIMin = Math.max(0, i - 2);
                int localIMax = Math.min(FIELD_SIZE - 1, i + 2);
                int localJMin = Math.max(0, j - 2);
                int localJMax = Math.min(FIELD_SIZE - 1, j + 2);
                if (!isCollision(field, i, j, localIMin, localIMax, localJMin, localJMax)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isCollision(
        int[][] field,
        int i,
        int j,
        int localIMin,
        int localIMax,
        int localJMin,
        int localJMax
    ) {
        if (field[i][j] == 1) {
            for (int lI = localIMin; lI <= localIMax; lI++) {
                for (int lJ = localJMin; lJ <= localJMax; lJ++) {
                    if (Math.abs(i - lI) < 2 && Math.abs(j - lJ) < 2) {
                        continue;
                    }
                    if (Math.abs(i - lI) == 2 && Math.abs(j - lJ) == 2) {
                        continue;
                    }
                    if (i == lI || j == lJ) {
                        continue;
                    }
                    if (field[lI][lJ] == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
