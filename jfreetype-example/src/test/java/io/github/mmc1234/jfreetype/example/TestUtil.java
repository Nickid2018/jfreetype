package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FTErrors;
import jdk.incubator.foreign.ResourceScope;

import static org.testng.Assert.assertEquals;

public class TestUtil {

    public static void assertOk(int errorCode) {
        assertEquals(errorCode, FTErrors.OK);
    }
}
