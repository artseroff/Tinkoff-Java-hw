package edu.hw2.Task1;

import edu.hw2.Task1.Expr.Addition;
import edu.hw2.Task1.Expr.Constant;
import edu.hw2.Task1.Expr.Negate;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class Task1Test {

    @ParameterizedTest
    @ValueSource(doubles = { -5, 0, 5, 1000, Double.MIN_VALUE, Double.MAX_VALUE })
    public void constant(double value) {
        double expected = value;

        Constant const1 = new Constant(value);
        double actual = const1.evaluate();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ValueSource(doubles = { -1000, -5, 0, 5, 1000, Double.MIN_VALUE, Double.MAX_VALUE })
    public void negate(double value) {
        double expected = -value;
        Negate negate = new Negate(value);

        double actual = negate.evaluate();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void addition(double value1, double value2) {
        Addition addition = new Addition(value1, value2);
        double expected = value1+value2;

        double actual = addition.evaluate();

        Assertions.assertEquals(expected, actual);
    }

    @SuppressWarnings("MagicNumber")
    private static Stream<Arguments> provideParameters() {
        return Stream.of(
            Arguments.of(5, 5),
            Arguments.of(5, -5),
            Arguments.of(0, 0),
            Arguments.of(12345, 678),
            Arguments.of(Double.MAX_VALUE, Double.MIN_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void multiplication(double value1, double value2) {
        Expr multiplication = new Expr.Multiplication(value1, value2);
        double expected = value1*value2;

        double actual = multiplication.evaluate();

        Assertions.assertEquals(expected, actual);
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void exponent(double value1, double value2) {
        Expr exponent = new Expr.Exponent(value1, value2);
        double expected = Math.pow(value1, value2);

        double actual = exponent.evaluate();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void complexTest1() {
        double expected = 37;

        var two = new Constant(2);
        var four = new Constant(4);
        var negOne = new Negate(new Constant(1));
        var sumTwoFour = new Addition(two, four);
        var mult = new Expr.Multiplication(sumTwoFour, negOne);
        var exp = new Expr.Exponent(mult, 2);
        var actual = new Addition(exp, new Constant(1)).evaluate();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void complexTest2() {
        // Example is 9/(3*(1+2))=1
        double expected = 1;

        var sumOneTwo = new Addition(1, 2);
        var mult = new Expr.Multiplication(3, sumOneTwo);
        var denominator = new Expr.Exponent(mult, -1);
        var multNineDenominator = new Expr.Multiplication(denominator, 9);
        var actual = multNineDenominator.evaluate();

        Assertions.assertEquals(expected, actual);
    }
}
