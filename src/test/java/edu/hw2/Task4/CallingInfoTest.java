package edu.hw2.Task4;

import java.util.function.Supplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CallingInfoTest {

    @Test
    public void callingInfoTest() {
        var info = CallingInfoUtils.callingInfo();

        Assertions.assertEquals(info.className(), this.getClass().getName());
        Assertions.assertEquals(info.methodName(), "callingInfoTest");
    }

    @Test
    public void callingInfoFirstNestLevel() {
        var info = callingInfoNested();

        Assertions.assertEquals(info.className(), this.getClass().getName());
        Assertions.assertEquals(info.methodName(), "callingInfoNested");
    }

    private CallingInfo callingInfoNested() {
        return CallingInfoUtils.callingInfo();
    }

    @Test
    public void callingInfoInsideLambda() {
        Supplier<CallingInfo> supplier = ()->CallingInfoUtils.callingInfo();
        var info = supplier.get();

        Assertions.assertEquals(info.className(), this.getClass().getName());
        Assertions.assertTrue(info.methodName().startsWith("lambda"));
        Assertions.assertTrue(info.methodName().contains("callingInfoInsideLambda"));
    }
}
