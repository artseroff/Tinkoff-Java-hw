package edu.hw1;

public class Task7 {

    private int rotate(int n, int shift, boolean rightShift) {
        if (n < 0) {
            throw new IllegalArgumentException("n must be >= 0");
        }
        if (n == 0) {
            return 0;
        }
        boolean localShiftFlag = (shift < 0) != rightShift;

        String binaryString = Integer.toBinaryString(n);
        int len = binaryString.length();
        int factShift = Math.abs(shift) % len;
        if (factShift == 0) {
            return n;
        }

        String shiftedBinary;
        if (localShiftFlag) {
            shiftedBinary = binaryString.substring(len - factShift) + binaryString.substring(0, len - factShift);
        } else {
            shiftedBinary = binaryString.substring(factShift) + binaryString.substring(0, factShift);
        }
        return Integer.parseInt(shiftedBinary, 2);
    }

    public int rotateLeft(int n, int shift) {
        return rotate(n, shift, false);
    }

    public int rotateRight(int n, int shift) {
        return rotate(n, shift, true);
    }
}
