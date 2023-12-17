package edu.hw11.task2;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.agent.ByteBuddyAgent;
import net.bytebuddy.dynamic.loading.ClassReloadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;

public class Task2 {
    @Test
    void redefineMethod() throws IOException {
        //Arrange
        ByteBuddyAgent.install();
        ClassReloadingStrategy classReloadingStrategy = ClassReloadingStrategy
            .fromInstalledAgent();

        //Act
        new ByteBuddy()
            .redefine(ArithmeticUtils.class)
            .method(ElementMatchers.named("sum"))
            .intercept(MethodDelegation.to(MockUtils.class))
            .make()
            .load(ArithmeticUtils.class.getClassLoader(), classReloadingStrategy);

        //Assert
        Assertions.assertEquals(5,ArithmeticUtils.sum(1,5));

        //Act&&Assert
        classReloadingStrategy.reset(ArithmeticUtils.class);
        Assertions.assertEquals(6,ArithmeticUtils.sum(1,5));
    }

    public static class ArithmeticUtils {
        public static int sum(int a, int b) {
            return a + b;
        }
    }

    public static class MockUtils {
        public static int sum(int a, int b) {
            return a * b;
        }
    }
}
