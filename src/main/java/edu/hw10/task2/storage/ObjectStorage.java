package edu.hw10.task2.storage;

import edu.hw10.task1.annotations.NotNull;
import java.util.Map;

public interface ObjectStorage {
    Object get(@NotNull String key);

    void put(@NotNull String key, Object value);

    Map<String, Object> getCache();
}
