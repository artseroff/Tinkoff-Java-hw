package edu.hw6.task1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class DiskMapTest {
    private static final Map<String,String> fileMap = Map.of("1", "one", "2", "two", "3", "three");

    private static final String INIT_FILE_CONTENT = """
        1:one
        2:two
        3:three
        """;


    private static final Path GOOD_FILE_PATH = Path.of("src/test/java/edu/hw6/task1/files/store1.txt");

    private void recoveFile() throws IOException {
        Files.writeString(GOOD_FILE_PATH, INIT_FILE_CONTENT);
    }

    @Test
    public void readMapFromFile() throws IOException {
        DiskMap diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        Assertions.assertEquals(fileMap.size(), diskMap.size());
        Assertions.assertEquals(!fileMap.isEmpty(), !diskMap.isEmpty());
        Assertions.assertTrue(diskMap.containsKey("3") && diskMap.containsValue("three"));
        Assertions.assertEquals(fileMap, diskMap);
    }

    @Test
    public void readMapFromFileWithInvalidValues() {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> DiskMap.readMapFromFile(GOOD_FILE_PATH.resolveSibling("storeFailures.txt")));
    }

    @Test
    public void readMapFromNonexistentFile() {
        Assertions.assertThrows(
            NoSuchFileException.class,
            () -> DiskMap.readMapFromFile(GOOD_FILE_PATH.resolveSibling("abacaba.txt")));
    }


    @Test
    public void readMapFromDirectoryPath() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> DiskMap.readMapFromFile(Path.of("src")));
    }

    @Test
    public void saveToFile() throws IOException {

        DiskMap diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        var newPath = GOOD_FILE_PATH.resolveSibling("newFile.txt");
        diskMap.saveToFile(newPath);
        diskMap = DiskMap.readMapFromFile(newPath);

        Assertions.assertEquals(fileMap.keySet(), diskMap.keySet());
        Assertions.assertEquals(fileMap.entrySet(), diskMap.entrySet());
        Assertions.assertEquals(fileMap, diskMap);

        Files.deleteIfExists(newPath);

    }

    @Test
    public void get() throws IOException {
        DiskMap diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);

        Assertions.assertEquals("one", diskMap.get("1"));
    }

    @Test
    public void put() throws IOException {
        DiskMap diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        String nullResult = diskMap.put("4", "four");
        Assertions.assertNull(nullResult);

        String threeValue = diskMap.put("3", "3");
        Assertions.assertEquals("three", threeValue);

        diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        Assertions.assertTrue(diskMap.containsKey("4"));
        Assertions.assertEquals(diskMap.get("3"), "3");
        Assertions.assertNull(diskMap.get("aboba"));

        recoveFile();
    }

    @Test
    public void remove() throws IOException {
        DiskMap diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        Assertions.assertNull(diskMap.remove(null));

        diskMap.remove("1");
        diskMap.remove("2");
        Assertions.assertEquals("three", diskMap.remove("3"));
        Assertions.assertTrue(diskMap.isEmpty());

        diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        Assertions.assertTrue(diskMap.isEmpty());

        recoveFile();
    }

    @Test
    public void putAll() throws IOException {
        var mapFourToSix = Map.of("4", "four", "5", "five", "6", "six");
        var fileMapClone = new HashMap<>(fileMap);
        fileMapClone.putAll(mapFourToSix);

        DiskMap diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        diskMap.putAll(mapFourToSix);
        diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);

        Assertions.assertEquals(fileMapClone,diskMap);
        recoveFile();

    }

    @Test
    public void clear() throws IOException {
        DiskMap diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        diskMap.clear();
        diskMap = DiskMap.readMapFromFile(GOOD_FILE_PATH);
        Assertions.assertTrue(diskMap.isEmpty());

        recoveFile();

    }
}
