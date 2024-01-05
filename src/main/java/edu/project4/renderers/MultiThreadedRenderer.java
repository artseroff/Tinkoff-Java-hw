package edu.project4.renderers;

import edu.project4.models.FractalImage;
import edu.project4.models.Rect;
import edu.project4.transformations.Affine;
import edu.project4.transformations.Transformation;
import java.util.List;

public class MultiThreadedRenderer implements Renderer {
    private final int countThreads;

    public MultiThreadedRenderer(int countThreads) {
        this.countThreads = Math.min(
            countThreads,
            Runtime.getRuntime().availableProcessors()
        );
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect borders,
        List<Affine> affines,
        List<Transformation> variations,
        int samples,
        int iterations,
        int symmetry
    ) {
        var threads = new Thread[countThreads];
        int samplesPerThread = samples / countThreads;

        for (int i = 0; i < countThreads; i++) {

            threads[i] = new Thread(() -> {
                var renderer = new SingleThreadedRenderer();
                renderer.render(
                    canvas,
                    borders,
                    affines,
                    variations,
                    samplesPerThread,
                    iterations,
                    symmetry
                );
            });

            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }

        return canvas;
    }
}
