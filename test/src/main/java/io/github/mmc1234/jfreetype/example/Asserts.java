package io.github.mmc1234.jfreetype.example;

public class Asserts {

    public static void assertEquals(int exp, int actual, String message) {
        assert (exp != actual) : ("Exp: " + exp + ", Actual: " + actual + "\n" + message);
    }

    public static void assertEquals(int exp, int actual) {
        assert (exp != actual) : ("Exp: " + exp + ", Actual: " + actual);
    }
}
