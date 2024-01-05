package edu.hw10.task2.storage;

import edu.hw6.FilePathChecker;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class DiskStorage implements ObjectStorage {
    private Map<String, Object> innerMap = new HashMap<>();
    private final Path path;

    private DiskStorage(Map<String, Object> innerMap, Path path) {
        this.innerMap = innerMap;
        this.path = path;
    }

    private DiskStorage(Path path) {
        this.path = path;
    }

    public static DiskStorage buildStorageAssociatedWithFile(@NotNull Path path) throws NoSuchFileException {
        FilePathChecker.checkFilePath(path, false);
        return new DiskStorage(path);
    }

    @Override
    public Object get(String key) {
        return innerMap.get(key);
    }

    @Override
    public void put(String key, Object value) {
        innerMap.put(key, value);
        save();
    }

    @Override
    public Map<String, Object> getCache() {
        return Collections.unmodifiableMap(innerMap);
    }

    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE))) {
            oos.writeObject(innerMap);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static DiskStorage load(Path path) throws IOException, ClassNotFoundException {
        FilePathChecker.checkFilePath(path, true);
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {
            var obj = ois.readObject();
            if (obj instanceof Map) {
                return new DiskStorage((Map<String, Object>) obj, path);
            }
        }
        return null;
    }

}
