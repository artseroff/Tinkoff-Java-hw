package edu.hw6.task1;

import edu.hw6.FilePathChecker;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class DiskMap implements Map<String, String> {
    private final Map<String, String> innerMap;
    private final Path path;
    private static final String KEY_VALUE_SEPARATOR = ":";

    private DiskMap(Path path, Map<String, String> map) {
        this.path = path;
        this.innerMap = map;
    }


    public static DiskMap readMapFromFile(Path path) throws IOException {
        FilePathChecker.checkFilePath(path);
        try (var streamLines = Files.lines(path)) {
            Map<String, String> map = streamLines
                .map(string -> {
                    String[] splitString = string.strip().split(KEY_VALUE_SEPARATOR);
                    return new AbstractMap.SimpleEntry<>(splitString[0], splitString[1]);
                })
                .collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue));
            return new DiskMap(path, map);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalStateException("Invalid values in file. Can't create map");
        }

    }

    public void saveToFile(Path path) throws NoSuchFileException {
        FilePathChecker.checkFilePath(path, false);
        try {
            Files.writeString(path, innerMapToString());
        } catch (IOException e) {
            throw new DiskMapSaveException(e.getMessage());
        }
    }

    private String innerMapToString() {
        StringBuilder result = new StringBuilder();
        for (Entry<String, String> entry : innerMap.entrySet()) {
            result.append("%s%s%s\n".formatted(entry.getKey(), KEY_VALUE_SEPARATOR, entry.getValue()));
        }
        return result.toString();
    }

    private void save() {
        try {
            Files.writeString(path, innerMapToString());
        } catch (IOException e) {
            throw new DiskMapSaveException(e.getMessage());
        }
    }

    @Override
    public int size() {
        return innerMap.size();
    }

    @Override
    public boolean isEmpty() {
        return innerMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return innerMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return innerMap.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return innerMap.get(key);
    }

    @Override
    public String put(@NotNull String key, String value) {
        String result = innerMap.put(key, value);
        save();
        return result;
    }

    @Override
    public String remove(Object key) {
        String result = innerMap.remove(key);
        if (result != null) {
            save();
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        m.forEach((key, value) -> {
            if (key == null) {
                throw new IllegalArgumentException("DiskMap doesn't support null keys");
            }
        });
        innerMap.putAll(m);
        save();
    }

    @Override
    public void clear() {
        innerMap.clear();
        save();
    }

    @Override
    public Set<String> keySet() {
        return innerMap.keySet();
    }

    @Override
    public Collection<String> values() {
        return innerMap.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return innerMap.entrySet();
    }
}
