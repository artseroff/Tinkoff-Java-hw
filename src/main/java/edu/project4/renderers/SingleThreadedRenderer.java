package edu.project4.renderers;

import edu.project4.models.FractalImage;
import edu.project4.models.Pixel;
import edu.project4.models.Point;
import edu.project4.models.Rect;
import edu.project4.transformations.Affine;
import edu.project4.transformations.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SingleThreadedRenderer implements Renderer {
//    private static final Color currentColor = new Color(0, 200, 200);
    public static final int START = -20;

    private final Random random = ThreadLocalRandom.current();

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect borders,
        List<Affine> affines, List<Transformation> variations,
        int samples,
        int iterations,
        int symmetry
    ) {
        for (int num = 0; num < samples; ++num) {
            Point point = getRandomPoint(borders);

            for (short step = START; step < iterations; ++step) {
                Affine affine = getRandomListElement(affines);
                Transformation variation = getRandomListElement(variations);
                point = variation.apply(affine.apply(point));
                Color currentColor = affine.getColor();
                if (step >= 0) {
                    double theta2 = 0.0;
                    for (int s = 0; s < symmetry; ++s) {
                        theta2 += Math.PI * 2.0 / symmetry;

                        Point rotatedPoint = rotate(point, theta2);
                        if (!borders.contains(rotatedPoint)) {
                            continue;
                        }

                        Pixel pixel = mapRange(borders, rotatedPoint, canvas);
                        if (pixel == null) {
                            continue;
                        }

                        synchronized (pixel) {
                            if (pixel.getHitCount() == 0) {
                                pixel.setR(currentColor.getRed());
                                pixel.setG(currentColor.getGreen());
                                pixel.setB(currentColor.getBlue());
                            } else {
                                pixel.setR((pixel.getR() + currentColor.getRed()) / 2);
                                pixel.setG((pixel.getG() + currentColor.getGreen()) / 2);
                                pixel.setB((pixel.getB() + currentColor.getBlue()) / 2);
                            }
                            pixel.addHit();
                        }
                    }
                }
            }
        }

        return canvas;
    }

    private Point getRandomPoint(Rect rect) {
        double randomX = rect.x() + random.nextDouble(0.0, rect.width());
        double randomY = rect.y() + random.nextDouble(0.0, rect.height());

        return new Point(randomX, randomY);
    }

    private <T extends Transformation> T getRandomListElement(List<T> list) {
        return list.get(random.nextInt(0, list.size()));
    }

    private Point rotate(Point point, double angle) {
        double x = point.x() * Math.cos(angle) - point.y() * Math.sin(angle);
        double y = point.x() * Math.sin(angle) + point.y() * Math.cos(angle);

        return new Point(x, y);
    }

    private Pixel mapRange(Rect borders, Point point, FractalImage canvas) {
        int x = (int) ((point.x() - borders.x()) * canvas.width() / borders.width());
        int y = (int) ((point.y() - borders.y()) * canvas.height() / borders.height());

        if (!canvas.contains(x, y)) {
            return null;
        }

        return canvas.getPixel(x, y);
    }
}
