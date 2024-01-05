package edu.hw10.task2;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

public class CacheProxy {
    private CacheProxy() {

    }

    public static Object build(@NotNull Object target, @NotNull Path cacheFile) throws IOException {
        var clazz = target.getClass();
        CacheProxyHandler handler = CacheProxyHandler.build(target, cacheFile);
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), handler);
    }

}
