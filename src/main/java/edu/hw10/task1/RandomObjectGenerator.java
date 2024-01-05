package edu.hw10.task1;

import edu.hw10.task1.arguments.IntegerGenerator;
import edu.hw10.task1.arguments.RandomArgumentGenerator;
import edu.hw10.task1.arguments.StringGenerator;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

public class RandomObjectGenerator {
    private static final Map<Class<?>, RandomArgumentGenerator> ARGUMENT_GENERATOR_MAP;

    static {
        var intGenerator = new IntegerGenerator();
        ARGUMENT_GENERATOR_MAP = Map.of(
            Integer.class, intGenerator,
            int.class, intGenerator,
            String.class, new StringGenerator()
        );
    }

    public Object nextObject(Class<?> target)
        throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = Arrays.stream(target.getDeclaredConstructors())
            .max(Comparator.comparingInt(Constructor::getParameterCount))
            .orElseThrow(
                () -> new NoSuchMethodException("%s does not have constructor".formatted(target))
            );
        constructor.setAccessible(true);
        return constructor.newInstance(generateArguments(constructor.getParameters()));
    }

    public Object nextObject(Class<?> target, @NotNull String factoryMethodName)
        throws InvocationTargetException, IllegalAccessException {
        Method method = Arrays.stream(target.getDeclaredMethods())
            .filter(
                m -> m.getReturnType().equals(target)
                    && m.getName().equals(factoryMethodName)
                    && !Modifier.isPrivate(m.getModifiers())
                    && Modifier.isStatic(m.getModifiers())
            )
            .max(Comparator.comparingInt(Method::getParameterCount))
            .orElseThrow(
                () -> new IllegalArgumentException(
                    "%s does not have factory method with name: '%s'"
                        .formatted(
                            target,
                            factoryMethodName
                        ))
            );
        return method.invoke(null, generateArguments(method.getParameters()));
    }

    private Object[] generateArguments(Parameter[] parameters) {
        Object[] arguments = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter currentParameter = parameters[i];
            RandomArgumentGenerator argGenerator = ARGUMENT_GENERATOR_MAP.get(currentParameter.getType());
            arguments[i] = argGenerator.next(currentParameter.getAnnotations());
        }
        return arguments;
    }
}
