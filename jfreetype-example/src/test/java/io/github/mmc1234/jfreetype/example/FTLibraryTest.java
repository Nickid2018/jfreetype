package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FTErrors;
import io.github.mmc1234.jfreetype.core.FreeType;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ValueLayout;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.github.mmc1234.jfreetype.core.FreeTypeLibrary.*;
import static org.testng.Assert.*;

public class FTLibraryTest {
    DefaultScope scope = new DefaultScope();

    @BeforeMethod
    public void setUp() {
        scope.setUp();

        assertTrue(FreeType.load());
    }

    @AfterMethod
    public void tearDown() {
        scope.tearDown();
    }

    @Test
    public void testCreateFTLibrary() {
        assertNotNull(createFTLibrary());
    }

    @Test
    public void testFTLibrary() {
        var lib = createFTLibrary();
        assertOk(FTInitFreeType(lib));
        assertOk(FTDoneFreeType(VarUtils.starAddress(lib)));
    }
    @Test
    public void testFTVersion() {
        var lib = createFTLibrary();
        FTInitFreeType(lib);
        var major = newInt();
        var minor = newInt();
        var patch = newInt();
        FTLibraryVersion(VarUtils.starAddress(lib), major, minor, patch);

        assertTrue(VarUtils.getInt(minor)>0);
        assertTrue(VarUtils.getInt(minor)>=0);
        assertTrue(VarUtils.getInt(minor)>=0);

        FTDoneFreeType(VarUtils.starAddress(lib));
    }

    public void assertOk(int errorCode) {
        assertEquals(errorCode, FTErrors.OK);
    }
    public MemorySegment createFTLibrary() {
        return VarUtils.newAddress(scope.get());
    }
    public MemorySegment newInt() {
        return MemorySegment.allocateNative(ValueLayout.JAVA_INT, scope.get());
    }
}
