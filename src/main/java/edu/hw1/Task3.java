package edu.hw1;

public class Task3 {

    private static final int MIN_VALID_ARR_LEN = 2;

    public boolean isNestable(int[] firstArray, int[] secondArray) {
        if (firstArray == null || secondArray == null) {
            throw new IllegalArgumentException("arrays must be not null");
        }
        int firstArrayLen = firstArray.length;
        int secondArrayLen = secondArray.length;

        if (firstArrayLen < MIN_VALID_ARR_LEN || secondArrayLen < MIN_VALID_ARR_LEN) {
            return false;
        }

        int maxLen = Math.max(firstArrayLen, secondArrayLen);

        Pair<Integer, Integer> minMaxFirstArr = new Pair<>(firstArray[0], firstArray[0]);
        Pair<Integer, Integer> minMaxSecondArr = new Pair<>(secondArray[0], secondArray[0]);

        for (int i = 0; i < maxLen; i++) {
            updateMinMaxValuesInArray(i, minMaxFirstArr, firstArrayLen, firstArray);
            updateMinMaxValuesInArray(i, minMaxSecondArr, secondArrayLen, secondArray);
        }

        return (minMaxFirstArr.getLeft() > minMaxSecondArr.getLeft()
            && minMaxFirstArr.getRight() < minMaxSecondArr.getRight());

    }

    private void updateMinMaxValuesInArray(int index, Pair<Integer, Integer> minMaxArr, int arrayLen, int[] a) {
        if (index < arrayLen) {
            if (a[index] < minMaxArr.getLeft()) {
                minMaxArr.setLeft(a[index]);
            }
            if (a[index] > minMaxArr.getRight()) {
                minMaxArr.setRight(a[index]);
            }
        }
    }

    private static class Pair<L, R> {
        private L left;
        private R right;

        private Pair(L left, R right) {
            this.left = left;
            this.right = right;
        }

        public L getLeft() {
            return left;
        }

        public void setLeft(L left) {
            this.left = left;
        }

        public R getRight() {
            return right;
        }

        public void setRight(R right) {
            this.right = right;
        }
    }
}
