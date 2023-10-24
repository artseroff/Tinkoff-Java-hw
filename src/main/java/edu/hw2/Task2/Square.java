package edu.hw2.Task2;

public class Square extends Rectangle {
    public Square(int side) {
        super(side, side);
    }

    @Override
    public Rectangle copyWithNewWidth(int width) {
        return new Square(width);
    }

    @Override
    public Rectangle copyWithNewHeight(int height) {
        return new Square(height);
    }
}
