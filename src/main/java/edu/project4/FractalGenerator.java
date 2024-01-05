package edu.project4;

import edu.project4.models.FractalImage;
import edu.project4.models.ImageFormat;
import edu.project4.models.Rect;
import edu.project4.processors.GammaCorrector;
import edu.project4.processors.ImageProcessor;
import edu.project4.renderers.MultiThreadedRenderer;
import edu.project4.renderers.Renderer;
import edu.project4.renderers.SingleThreadedRenderer;
import edu.project4.transformations.Affine;
import edu.project4.transformations.Transformation;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@SuppressWarnings({"ParameterNumber"})
public class FractalGenerator {
    private FractalGenerator() {
    }

    public static FractalImage generate(
        int width,
        int height,
        Rect borders,
        List<Affine> affines,
        List<Transformation> variations,
        int samples,
        int iterPerSample,
        int symmetry,
        double gamma,
        int countThreads,
        Path path,
        ImageFormat format
    ) throws IOException {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }

        if (height <= 0) {
            throw new IllegalArgumentException("Height must be positive");
        }

        if (samples <= 0) {
            throw new IllegalArgumentException("Samples must be positive");
        }

        if (iterPerSample <= 0) {
            throw new IllegalArgumentException("Iter per sample must be positive");
        }

        if (symmetry <= 0) {
            throw new IllegalArgumentException("Symmetry must be positive");
        }

        if (countThreads <= 0) {
            throw new IllegalArgumentException("Count threads must be positive");
        }

        if (variations.isEmpty()) {
            throw new IllegalArgumentException("Variation list must not be empty");
        }

        if (!path.toString().endsWith("." + format.name().toLowerCase())) {
            throw new IllegalArgumentException("Incorrect file extension");
        }

        var canvas = FractalImage.create(width, height);

        Renderer renderer = countThreads == 1 ? new SingleThreadedRenderer() : new MultiThreadedRenderer(countThreads);

        FractalImage renderedImage = renderer.render(
            canvas,
            borders,
            affines,
            variations,
            samples,
            iterPerSample,
            symmetry
        );

        if (gamma != 0.0) {
            ImageProcessor processor = new GammaCorrector(gamma);
            processor.process(renderedImage);
        }

        ImageUtils.save(renderedImage, path, format);

        return renderedImage;
    }
}
