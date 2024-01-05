package edu.hw11.task3;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class Task3Test {
    private static final String CLASS_NAME = "FibonacciCalculator";
    private static final String METHOD_NAME = "fib";
    private static final String METHOD_SIGNATURE = "(I)J";

    @SuppressWarnings("MagicNumber")
    private static Arguments[] params() {
        return new Arguments[] {
            Arguments.of(-50, 0L),
            Arguments.of(0, 0L),
            Arguments.of(1, 1L),
            Arguments.of(2, 1L),
            Arguments.of(7, 13L),
            Arguments.of(9, 34L)
        };
    }

    @ParameterizedTest
    @MethodSource("params")
    void fibTest(int n, long expected) throws Exception {
        //Arrange
        Class<?> fibonacciCalculator = buildClassWithByteBuddyAndASM();

        //Act
        long result = (long) fibonacciCalculator
            .getMethod(METHOD_NAME, int.class)
            .invoke(null, n);

        //Assert
        Assertions.assertEquals(expected, result);
    }

    private Class<?> buildClassWithByteBuddyAndASM() throws IOException {

        try (DynamicType.Unloaded<Object> unloadedType = new ByteBuddy()
            .subclass(Object.class)
            .name(CLASS_NAME)
            .defineMethod(METHOD_NAME, long.class, Modifier.PUBLIC | Modifier.STATIC)
            .withParameters(int.class)
            .intercept(new Implementation.Simple(new FibonacciAppender()))
            .make()) {

            Files.write(
                Path.of("src/test/java/edu/hw11/task3/%s.class".formatted(CLASS_NAME)),
                unloadedType.getBytes()
            );

            return unloadedType
                .load(getClass().getClassLoader())
                .getLoaded();
        }
    }

    private static class FibonacciAppender implements ByteCodeAppender {
        @Override
        public Size apply(
            MethodVisitor mv,
            Implementation.Context context,
            MethodDescription md
        ) {
            mv.visitCode();
            // if (n < 0)
            Label l0 = new Label();
            mv.visitVarInsn(Opcodes.ILOAD, 0);
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitJumpInsn(Opcodes.IF_ICMPGE, l0);

            // return 0
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitInsn(Opcodes.I2L);
            mv.visitInsn(Opcodes.LRETURN);

            // if (n >= 0)
            mv.visitLabel(l0);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            // if (n <= 1)
            Label l1 = new Label();
            mv.visitVarInsn(Opcodes.ILOAD, 0);
            mv.visitInsn(Opcodes.ICONST_1);
            mv.visitJumpInsn(Opcodes.IF_ICMPGT, l1);

            // return n
            mv.visitVarInsn(Opcodes.ILOAD, 0);
            mv.visitInsn(Opcodes.I2L);
            mv.visitInsn(Opcodes.LRETURN);

            // if (n > 1)
            mv.visitLabel(l1);
            mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);

            // f(n - 1)
            mv.visitVarInsn(Opcodes.ILOAD, 0);
            mv.visitInsn(Opcodes.ICONST_1);
            mv.visitInsn(Opcodes.ISUB);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_NAME, METHOD_NAME, METHOD_SIGNATURE);

            // f(n - 2)
            mv.visitVarInsn(Opcodes.ILOAD, 0);
            mv.visitInsn(Opcodes.ICONST_2);
            mv.visitInsn(Opcodes.ISUB);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, CLASS_NAME, METHOD_NAME, METHOD_SIGNATURE);

            // f(n - 1) + f(n - 2);
            mv.visitInsn(Opcodes.LADD);
            mv.visitInsn(Opcodes.LRETURN);
            mv.visitEnd();

            return new ByteCodeAppender.Size(4, 1);
        }
    }
}
