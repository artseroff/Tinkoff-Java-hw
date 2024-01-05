package edu.project4.transformations;

import edu.project4.models.Point;
import java.awt.Color;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Affine implements Transformation {
    private final static int BORDER = 1;
    private final static int MAX_COLOR = 256;
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;
    private final Color color;

    private Affine(double a, double b, double c, double d, double e, double f, Color color) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
        this.color = color;
    }

    @Override
    public Point apply(Point point) {
        double x = a * point.x() + b * point.y() + c;
        double y = d * point.x() + e * point.y() + f;

        return new Point(x, y);
    }

    public Color getColor() {
        return color;
    }

    public static Affine getRandomAffineTransform() {
        Random rand = ThreadLocalRandom.current();
        double a;
        double b;
        double d;
        double e;

        do {
            do {
                a = rand.nextDouble(-BORDER, BORDER);
                d = rand.nextDouble(-BORDER, BORDER);
            } while ((a * a + d * d) > 1);
            do {
                b = rand.nextDouble(-BORDER, BORDER);
                e = rand.nextDouble(-BORDER, BORDER);
            } while ((b * b + e * e) > 1);
        } while ((a * a + b * b + d * d + e * e)
            > (1 + (a * e - d * b) * (a * e - d * b)));

        double c = rand.nextDouble(-BORDER, BORDER);
        double f = rand.nextDouble(-BORDER, BORDER);

        int red = rand.nextInt(0, MAX_COLOR);
        int green = rand.nextInt(0, MAX_COLOR);
        int blue = rand.nextInt(0, MAX_COLOR);
        Color color = new Color(red, green, blue);

        return new Affine(a, b, c, d, e, f, color);
    }
}
