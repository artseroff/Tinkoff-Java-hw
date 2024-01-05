package edu.hw10.task2.storage;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class InMemoryStorage implements ObjectStorage {
    private final Map<String, Object> map = new HashMap<>();

    @Override
    public Object get(String key) {
        return map.get(key);
    }

    @Override
    public void put(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public Map<String, Object> getCache() {
        return Collections.unmodifiableMap(map);
    }
}
