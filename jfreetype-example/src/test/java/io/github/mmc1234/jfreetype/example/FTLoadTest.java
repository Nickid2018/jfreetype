package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FreeType;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class FTLoadTest {
    @Test
    public void testLoad() {
        assertTrue(FreeType.load());
    }
}
