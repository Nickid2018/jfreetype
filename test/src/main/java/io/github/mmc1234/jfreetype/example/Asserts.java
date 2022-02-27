package io.github.mmc1234.jfreetype.example;

public class Asserts {
    public static final void assertEquals(int exp, int actual, String message) {
        if(exp != actual)
            throw new AssertionError("Exp: "+exp+", Actual: "+actual+"\n"+message);
    }
    public static final void assertEquals(int exp, int actual) {
        if(exp != actual)
            throw new AssertionError("Exp: "+exp+", Actual: "+actual);
    }
}
