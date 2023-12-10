package edu.project4;

import edu.project4.models.ImageFormat;
import edu.project4.models.Rect;
import edu.project4.transformations.Affine;
import edu.project4.transformations.Linear;
import edu.project4.transformations.Swirl;
import edu.project4.transformations.Transformation;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@Disabled
public final class RenderersTest {
    private final static int WIDTH = 1920;
    private final static int HEIGHT = 1080;
    private final static Rect BORDERS = new Rect(-1.5, -1.5, 3.0, 3.0);
    private final static int SAMPLES = 30_000;
    private final static int ITER_PER_SAMPLE = 1500;
    private final static int SYMMETRY = 1;
    private final static double GAMMA = 1.2;
    private final static int AFFINE_TRANSFORMS_COUNT = 15;

    private final static Logger LOGGER = LogManager.getLogger();

    @TempDir
    public Path IMAGES_FOLDER;

    @Test
    void performanceTest() throws IOException {

        List<Affine> affines = Stream.generate(Affine::getRandomAffineTransform)
            .limit(AFFINE_TRANSFORMS_COUNT)
            .collect(Collectors.toList());

        List<Transformation> variations = List.of(new Linear(), new Swirl());

        ImageFormat format = ImageFormat.PNG;
        Function<Void,String> fileNameFunc = unused -> String.format(
            "%s.%s",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")), format.name().toLowerCase()
        );

        Path path = IMAGES_FOLDER.resolve(fileNameFunc.apply(null));

        long timeStart = System.nanoTime();
        FractalGenerator.generate(
            WIDTH,
            HEIGHT,
            BORDERS,
            affines,
            variations,
            SAMPLES,
            ITER_PER_SAMPLE,
            SYMMETRY,
            GAMMA,
            1,
            path,
            format
        );
        long nanoEstimatedTime = System.nanoTime();
        long single = nanoEstimatedTime - timeStart;
        LOGGER.info("Single thread:\n%s".formatted(timeToString(single)));
        Assertions.assertTrue(Files.readAllBytes(path).length>0);

        path = IMAGES_FOLDER.resolve(fileNameFunc.apply(null));
        int countThreads = Runtime.getRuntime().availableProcessors();

        timeStart = System.nanoTime();
        FractalGenerator.generate(
            WIDTH,
            HEIGHT,
            BORDERS,
            affines,
            variations,
            SAMPLES,
            ITER_PER_SAMPLE,
            SYMMETRY,
            GAMMA,
            countThreads,
            path,
            format
        );
        nanoEstimatedTime = System.nanoTime();
        long multi = nanoEstimatedTime - timeStart;
        Assertions.assertTrue(Files.readAllBytes(path).length>0);
        LOGGER.info("%d threads:\n%s".formatted(countThreads,timeToString(multi)));
        LOGGER.info("Multithread acceleration is: " + (1.0 * single / multi));
    }

    private String timeToString(long time) {
        return "Work time: %d sec %d millis %d micro %d nano\n"
            .formatted(time / 1_000_000_000, time / 1_000_000 % 1_000, time / 1_000 % 1_000, time % 1_000);
    }
}
