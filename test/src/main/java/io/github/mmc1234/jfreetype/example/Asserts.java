package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FTErrors;

public class Asserts {

    public static void assertEquals(int exp, int actual, String message) {
        if (exp != actual) throw new AssertionError("Exp: " + exp + ", Actual: " + actual + "\n" + message);
    }

    public static void assertEquals(int exp, int actual) {
        if (exp != actual) throw new AssertionError("Exp: " + exp + ", Actual: " + actual);
    }

    public static void checkErrorCode(int errorCode) {
        if (FTErrors.isSuccess(errorCode))
            return;
        throw new AssertionError(FTErrors.toString(errorCode));
    }
}
