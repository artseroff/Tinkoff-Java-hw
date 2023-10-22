package edu.hw2.Task1;

public sealed interface Expr {

    double evaluate();

    record Constant(double value) implements Expr {
        @Override
        public double evaluate() {
            return value;
        }
    }

    record Negate(double value) implements Expr {
        public Negate(double value) {
            this.value = -value;
        }

        public Negate(Expr expr) {
            this(-expr.evaluate());
        }

        @Override
        public double evaluate() {
            return value;
        }
    }

    record Exponent(double base, double power) implements Expr {

        public Exponent(Expr base, double power) {
            this(base.evaluate(), power);
        }

        public Exponent(Expr base, Expr power) {
            this(base.evaluate(), power.evaluate());
        }

        public Exponent(double base, Expr power) {
            this(base, power.evaluate());
        }

        @Override
        public double evaluate() {
            return Math.pow(base, power);
        }
    }

    record Addition(double value1, double value2) implements Expr {

        public Addition(double value1, Expr expr2) {
            this(value1, expr2.evaluate());
        }

        public Addition(Expr expr1, Expr expr2) {
            this(expr1.evaluate(), expr2.evaluate());
        }

        public Addition(Expr expr1, double value2) {
            this(expr1.evaluate(), value2);
        }

        @Override
        public double evaluate() {
            return value1 + value2;
        }
    }

    record Multiplication(double value1, double value2) implements Expr {

        public Multiplication(double value1, Expr expr2) {
            this(value1, expr2.evaluate());
        }

        public Multiplication(Expr expr1, Expr expr2) {
            this(expr1.evaluate(), expr2.evaluate());
        }

        public Multiplication(Expr expr1, double value2) {
            this(expr1.evaluate(), value2);
        }

        @Override
        public double evaluate() {
            return value1 * value2;
        }
    }
}

