package edu.hw11.task1;

import java.lang.reflect.InvocationTargetException;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task1Test {
    private static final String MESSAGE = "Hello, ByteBuddy!";

    @Test
    void helloByteBuddyTest()
        throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        //Arrange
        try (DynamicType.Unloaded<Object> unloadedType = new ByteBuddy()
            .subclass(Object.class)
            .method(ElementMatchers.isToString())
            .intercept(FixedValue.value(MESSAGE))
            .make()) {

            Class<?> dynamicType = unloadedType
                .load(getClass().getClassLoader())
                .getLoaded();

            //Act
            var obj = dynamicType.getDeclaredConstructor().newInstance();

            //Assert
            Assertions.assertEquals(MESSAGE, obj.toString());
        }


    }
}
