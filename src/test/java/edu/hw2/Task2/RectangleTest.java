package edu.hw2.Task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RectangleTest {

    public static Arguments[] rectangles() {
        return new Arguments[] {
            Arguments.of(new Rectangle(20, 5)),
            Arguments.of(new Square(10))
        };
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    public void areaTest(Rectangle rect) {
        Assertions.assertEquals(rect.area(), rect.getHeight() * rect.getWidth());
    }

    @Test
    public void copyWithNewWidthRectTest() {
        Rectangle rect1 = new Rectangle(20, 5);

        int newValue = 100;

        var rect2 = rect1.copyWithNewWidth(newValue);

        Assertions.assertEquals(rect2.getWidth(), newValue);
        Assertions.assertEquals(rect1.getHeight(), rect2.getHeight());
        Assertions.assertNotEquals(rect1.getWidth(), rect2.getWidth());
    }

    @Test
    public void copyWithNewWidthSquareTest() {
        Rectangle rect1 = new Square(10);
        int newValue = 100;

        var rect2 = rect1.copyWithNewWidth(newValue);

        Assertions.assertEquals(rect2.getWidth(), newValue);
        Assertions.assertEquals(rect2.getHeight(), rect2.getWidth());
        Assertions.assertNotEquals(rect1.getWidth(), rect2.getWidth());
        Assertions.assertNotEquals(rect1.getHeight(), rect2.getHeight());
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    public void changeHeightTestRectImmutable(Rectangle rect1) {
        int heightBeforeSet = rect1.getHeight();

        var rect2 = rect1.copyWithNewHeight(100);
        int heightAfterSet = rect1.getHeight();

        Assertions.assertEquals(heightBeforeSet, heightAfterSet);
        Assertions.assertNotEquals(rect1, rect2);
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    public void changeWidthTestRectImmutable(Rectangle rect1) {
        int widthBeforeSet = rect1.getWidth();

        var rect2 = rect1.copyWithNewWidth(100);
        int widthAfterSet = rect1.getWidth();

        Assertions.assertEquals(widthBeforeSet, widthAfterSet);
        Assertions.assertNotEquals(rect1, rect2);
    }

}
