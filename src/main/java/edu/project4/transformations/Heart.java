package edu.project4.transformations;

import edu.project4.models.Point;

public class Heart implements Transformation {
    @Override
    public Point apply(Point point) {
        double radius = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan(point.x() / point.y());

        double x = radius * Math.sin(radius * theta);
        double y = radius * -Math.cos(radius * theta);

        return new Point(x, y);
    }
}
