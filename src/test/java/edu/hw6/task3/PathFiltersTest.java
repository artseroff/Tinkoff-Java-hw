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
        DirectoryStream.Filter<Path> filter = PathFilters.largerThan(10_000);
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {
            var iter = entries.iterator();
            Assertions.assertEquals("bmp-801kb.bmp", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void lessThan() throws IOException {
        DirectoryStream.Filter<Path> filter = PathFilters.lessThan(2000);
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {
            var iter = entries.iterator();
            Assertions.assertEquals("TestFile.txt", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void regexMatches() throws IOException {
        DirectoryStream.Filter<Path> filter = PathFilters.regexMatches(".*[-]8.*");
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {
            var iter = entries.iterator();
            Assertions.assertEquals("bmp-801kb.bmp", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void writableReadable() throws IOException {
        DirectoryStream.Filter<Path> filter = PathFilters.READABLE.and(PathFilters.WRITABLE);
        try (DirectoryStream<Path> entriesWithFilter = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter);
             DirectoryStream<Path> entriesWithoutFilter = Files.newDirectoryStream(TEST_FILES_DIRECTORY)) {

            Assertions.assertEquals(
                entriesWithoutFilter.spliterator().estimateSize(),
                entriesWithFilter.spliterator().estimateSize()
            );
        }
    }

    @Test
    public void globNoFiles() throws IOException {
        GlobMatchesFilter txtFilter = new GlobMatchesFilter("*.txt");
        GlobMatchesFilter pngFilter = new GlobMatchesFilter("*.png");
        DirectoryStream.Filter<Path> filter = txtFilter.and(pngFilter);

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, filter)) {

            Assertions.assertFalse(entries.iterator().hasNext());
        }
    }

    @Test
    public void globPngFiles() throws IOException {
        GlobMatchesFilter pngFilter = new GlobMatchesFilter("*.png");

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, pngFilter)) {
            var iter = entries.iterator();
            Assertions.assertEquals("img.png", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void MagicNumberFilterPngFiles() throws IOException {
        MagicNumberFilter pngFilter = new MagicNumberFilter(0x89, 'P', 'N', 'G');

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, pngFilter)) {
            var iter = entries.iterator();
            Assertions.assertEquals("img.png", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void MagicNumberFilterBmpFiles() throws IOException {
        MagicNumberFilter pngFilter = new MagicNumberFilter('B', 'M');

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(TEST_FILES_DIRECTORY, pngFilter)) {
            var iter = entries.iterator();
            Assertions.assertEquals("bmp-801kb.bmp", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }

    @Test
    public void complex() throws IOException {
        DirectoryStream.Filter<Path> filter = PathFilters.WRITABLE
            .and(PathFilters.READABLE)
            .and(PathFilters.largerThan(10))
            .and(new MagicNumberFilter(0x89, 'P', 'N', 'G'))
            .and(new GlobMatchesFilter("*.png"))
            .and(PathFilters.regexMatches(".*i.*"));

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(
            TEST_FILES_DIRECTORY,
            filter
        )) {
            var iter = entries.iterator();
            Assertions.assertEquals("img.png", iter.next().getFileName().toString());
            Assertions.assertFalse(iter.hasNext());
        }
    }
}
