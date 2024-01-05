package edu.project4;

import edu.project4.models.ImageFormat;
import edu.project4.models.Rect;
import edu.project4.transformations.Affine;
import edu.project4.transformations.Disc;
import edu.project4.transformations.Heart;
import edu.project4.transformations.Linear;
import edu.project4.transformations.Sinusoidal;
import edu.project4.transformations.Spherical;
import edu.project4.transformations.Swirl;
import edu.project4.transformations.Transformation;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Main {
    private final static int WIDTH = 1920;
    private final static int HEIGHT = 1080;
    private final static Rect BORDERS = new Rect(-1.5, -1.5, 3.0, 3.0);
    private final static int SAMPLES = 30_000;
    private final static int ITER_PER_SAMPLE = 1500;
    private final static int SYMMETRY = 1;
    private final static double GAMMA = 1.2;
    private final static int AFFINE_TRANSFORMS_COUNT = 15;
    public static final Path IMAGES_FOLDER = Path.of("src/main/java/edu/project4/images/");

    private Main() {
    }

    public static void main(String[] args) throws IOException {
        List<Affine> affines = Stream.generate(Affine::getRandomAffineTransform)
            .limit(AFFINE_TRANSFORMS_COUNT)
            .collect(Collectors.toList());

        List<Transformation> variations = new ArrayList<>();
        variations.add(new Linear());
        variations.add(new Sinusoidal());
        variations.add(new Spherical());
        variations.add(new Heart());
        variations.add(new Swirl());
        variations.add(new Disc());

        ImageFormat format = ImageFormat.PNG;
        String fileName = String.format(
            "%s.%s",
            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss")), format.name().toLowerCase()
        );
        Path path = IMAGES_FOLDER.resolve(fileName);

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
            Runtime.getRuntime().availableProcessors(),
            path,
            format
        );
    }
}
