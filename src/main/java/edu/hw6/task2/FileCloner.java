package edu.hw6.task2;

import edu.hw6.FilePathChecker;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.jetbrains.annotations.NotNull;

public class FileCloner {

    public static final String COPY_APPENDIX = " - копия";

    private FileCloner() {
    }

    public static void cloneFile(@NotNull Path pathSource) throws IOException {

        FilePathChecker.checkFilePath(pathSource, true);
        Path pathToCopy = foundActualCloneName(pathSource);
        Files.copy(pathSource, pathToCopy, StandardCopyOption.COPY_ATTRIBUTES, LinkOption.NOFOLLOW_LINKS);
    }

    private static Path foundActualCloneName(Path source) {
        String filenameWithExtension = source.getFileName().toString();
        String[] splitName = filenameWithExtension.split("\\.(?=[^.]*$)");
        String filename = splitName[0];
        String extension = "";
        if (splitName.length > 1) {
            extension = "." + splitName[1];
        }

        String firstCloneName = filename + COPY_APPENDIX + extension;
        Path firstClonePath = source.resolveSibling(firstCloneName);
        if (!Files.exists(firstClonePath)) {
            return firstClonePath;
        }
        int counter = 1;

        Path currentClonePath;
        do {
            counter++;
            String currentCloneName = filename + COPY_APPENDIX + " (" + counter + ")" + extension;
            currentClonePath = source.resolveSibling(currentCloneName);
        } while (Files.exists(currentClonePath));
        return currentClonePath;
    }

}
