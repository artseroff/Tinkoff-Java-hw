package edu.project4;

import edu.project4.models.FractalImage;
import edu.project4.models.ImageFormat;
import edu.project4.models.Pixel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class ImageUtilsTest {
    @TempDir
    public Path IMAGES_FOLDER;
    @ParameterizedTest
    @EnumSource(ImageFormat.class)
    void save(ImageFormat format) throws IOException {
        var path = IMAGES_FOLDER.resolve("ImageUtilsTest."+format.name().toLowerCase());
        Assertions.assertFalse(Files.exists(path));

        var data = new Pixel[]{new Pixel(0,0,0,0)};
        var image = new FractalImage(data,1,1);
        ImageUtils.save(image,path,format);

        Assertions.assertTrue(Files.exists(path));
        Assertions.assertTrue(Files.readAllBytes(path).length>0);
    }
}
