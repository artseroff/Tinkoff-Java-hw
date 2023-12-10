package edu.project4.renderers;

import edu.project4.models.FractalImage;
import edu.project4.models.Rect;
import edu.project4.transformations.Affine;
import edu.project4.transformations.Transformation;
import java.util.List;

@FunctionalInterface
public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        Rect borders,
        List<Affine> affines,
        List<Transformation> variations,
        int samples,
        int iterations,
        int symmetry
    );
}
