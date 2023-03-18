package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FTErrors;
import io.github.mmc1234.jfreetype.core.FreeType;
import io.github.mmc1234.jfreetype.util.Scope;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemorySegment;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.github.mmc1234.jfreetype.core.FreeTypeLibrary.*;
import static org.testng.Assert.*;

public class FTLibraryTest {

    private Scope scope;

    @BeforeMethod
    public void start() {
        assertTrue(FreeType.load());
        scope = Scope.newScope();
    }

    @AfterMethod
    public void over() {
        scope.close();
    }

    @Test
    public void testCreateFTLibrary() {
        assertNotNull(newAddress());
    }

    @Test
    public void testFTLibrary() {
        MemorySegment lib = newAddress();
        assertOk(FTInitFreeType(lib));
        assertOk(FTDoneFreeType(VarUtils.starAddress(lib)));
    }

    @Test
    public void testFTVersion() {
        MemorySegment lib = newAddress();
        FTInitFreeType(lib);
        MemorySegment major = newInt();
        MemorySegment minor = newInt();
        MemorySegment patch = newInt();
        FTLibraryVersion(VarUtils.starAddress(lib), major, minor, patch);

        assertTrue(VarUtils.getInt(minor) > 0);
        assertTrue(VarUtils.getInt(minor) >= 0);
        assertTrue(VarUtils.getInt(minor) >= 0);

        System.out.println("FreeType Version: " + VarUtils.getInt(major) + "." + VarUtils.getInt(minor) + "." + VarUtils.getInt(patch));

        FTDoneFreeType(VarUtils.starAddress(lib));
    }

    public void assertOk(int errorCode) {
        assertEquals(errorCode, FTErrors.OK);
    }

    public MemorySegment newAddress() {
        return scope.newAddress();
    }

    public MemorySegment newInt() {
        return scope.newInt();
    }
}
