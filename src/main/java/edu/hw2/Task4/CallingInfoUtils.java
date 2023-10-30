package edu.hw2.Task4;

public class CallingInfoUtils {
    private CallingInfoUtils() {
    }

    public static CallingInfo callingInfo() {
        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
        return new CallingInfo(stackTrace[1].getClassName(), stackTrace[1].getMethodName());
    }
}
