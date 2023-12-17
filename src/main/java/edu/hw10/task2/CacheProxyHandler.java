package edu.hw10.task2;

import edu.hw10.task1.annotations.NotNull;
import edu.hw10.task2.storage.DiskStorage;
import edu.hw10.task2.storage.InMemoryStorage;
import edu.hw10.task2.storage.ObjectStorage;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Arrays;

public class CacheProxyHandler implements InvocationHandler {
    private static final String ARGS_SEPARATOR = "; ";

    private final ObjectStorage inMemoryCache = new InMemoryStorage();
    private final ObjectStorage diskCache;
    private final Object target;

    private CacheProxyHandler(@NotNull Object target, @NotNull DiskStorage diskStorage) {
        this.target = target;
        this.diskCache = diskStorage;

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var cacheAnnotation = method.getAnnotation(Cache.class);

        if (cacheAnnotation == null) {
            return method.invoke(target, args);
        }
        ObjectStorage currentStorage;

        if (cacheAnnotation.persist()) {
            currentStorage = diskCache;
        } else {
            currentStorage = inMemoryCache;
        }
        String key = generateKey(method, args);

        Object storageResult = currentStorage.get(key);
        if (storageResult != null) {
            return storageResult;
        }

        Object invokeResult = method.invoke(target, args);

        currentStorage.put(key, invokeResult);
        return invokeResult;
    }

    private String generateKey(Method method, Object[] args) {
        var argsString = String.join(ARGS_SEPARATOR, Arrays.stream(args)
            .map(Object::toString)
            .toList());
        return "%s(%s)".formatted(method.getName(), argsString);
    }

    public static CacheProxyHandler build(Object target, Path cacheFile) throws IOException {
        var diskStorage = DiskStorage.buildStorageAssociatedWithFile(cacheFile);
        return new CacheProxyHandler(target, diskStorage);
    }
}
