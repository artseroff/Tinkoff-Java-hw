package edu.hw2.Task2;

public class Rectangle {
    private final int width;
    private final int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Rectangle copyWithNewWidth(int width) {
        return new Rectangle(width, height);
    }

    public Rectangle copyWithNewHeight(int height) {
        return new Rectangle(width, height);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double area() {
        return width * height;
    }
}
