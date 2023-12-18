package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PathFiltersTest {

    private static final Path TEST_FILES_DIRECTORY = Path.of("src/test/java/edu/hw6/task3/files");

    @Test
    public void largerThan() throws IOException {
        //Arrange
        DirectoryStream.Filter<Path> filter = PathFilters.largerThan(10_000);

        //Act
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {
            var iter = entries.iterator();

            //Assert
            Assertions.assertEquals("bmp-801kb.bmp", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void lessThan() throws IOException {
        //Arrange
        DirectoryStream.Filter<Path> filter = PathFilters.lessThan(2000);

        //Act
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {
            var iter = entries.iterator();

            //Assert
            Assertions.assertEquals("TestFile.txt", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void regexMatches() throws IOException {
        //Arrange
        DirectoryStream.Filter<Path> filter = PathFilters.regexMatches(".*[-]8.*");

        //Act
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {
            var iter = entries.iterator();

            //Assert
            Assertions.assertEquals("bmp-801kb.bmp", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void writableReadable() throws IOException {
        //Arrange
        DirectoryStream.Filter<Path> filter = PathFilters.READABLE.and(PathFilters.WRITABLE);

        //Act
        try (DirectoryStream<Path> entriesWithFilter = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter);
             DirectoryStream<Path> entriesWithoutFilter = Files.newDirectoryStream(TEST_FILES_DIRECTORY)) {

            //Assert
            Assertions.assertEquals(
                entriesWithoutFilter.spliterator().estimateSize(),
                entriesWithFilter.spliterator().estimateSize()
            );
        }
    }

    @Test
    public void globNoFiles() throws IOException {
        //Arrange
        GlobMatchesFilter txtFilter = new GlobMatchesFilter("*.txt");
        GlobMatchesFilter pngFilter = new GlobMatchesFilter("*.png");
        DirectoryStream.Filter<Path> filter = txtFilter.and(pngFilter);

        //Act
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {

            //Assert
            Assertions.assertFalse(entries.iterator().hasNext());
        }
    }

    @Test
    public void globOrTest() throws IOException {
        //Arrange
        GlobMatchesFilter txtFilter = new GlobMatchesFilter("*.txt");
        GlobMatchesFilter pngFilter = new GlobMatchesFilter("*.png");
        GlobMatchesFilter bmpFilter = new GlobMatchesFilter("*.bmp");

        DirectoryStream.Filter<Path> filter = txtFilter.or(pngFilter).or(bmpFilter);

        //Act
        int countFiles = 0;
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {

            for (Path ignored : entries) {
                countFiles++;
            }
        }

        //Assert
        Assertions.assertEquals(3, countFiles);
    }

    @Test
    public void globPngFiles() throws IOException {
        //Arrange
        GlobMatchesFilter pngFilter = new GlobMatchesFilter("*.png");

        //Act
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, pngFilter)) {
            var iter = entries.iterator();

            //Assert
            Assertions.assertEquals("img.png", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void MagicNumberFilterPngFiles() throws IOException {
        //Arrange
        MagicNumberFilter pngFilter = new MagicNumberFilter(0x89, 'P', 'N', 'G');

        //Act
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, pngFilter)) {
            var iter = entries.iterator();

            //Assert
            Assertions.assertEquals("img.png", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void MagicNumberFilterBmpFiles() throws IOException {
        //Arrange
        MagicNumberFilter pngFilter = new MagicNumberFilter('B', 'M');

        //Act
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, pngFilter)) {
            var iter = entries.iterator();

            //Assert
            Assertions.assertEquals("bmp-801kb.bmp", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void complex() throws IOException {
        //Arrange
        DirectoryStream.Filter<Path> filter = PathFilters.WRITABLE
            .and(PathFilters.READABLE)
            .and(PathFilters.largerThan(10))
            .and(new MagicNumberFilter(0x89, 'P', 'N', 'G'))
            .and(new GlobMatchesFilter("*.png"))
            .and(PathFilters.regexMatches(".*i.*"));

        //Act
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(
            TEST_FILES_DIRECTORY,
            filter
        )) {

            //Assert
            var iter = entries.iterator();
            Assertions.assertEquals("img.png", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }
}
