package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.internal.BaseInterface;
import io.github.mmc1234.jfreetype.internal.VersionInternal;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

public interface FreeTypeLibrary {

    /**
     * Return the version of the FreeType library being used.
     * This is useful when dynamically linking to the library,
     * since one cannot use the macros {@code FREETYPE_MAJOR}, {@code FREETYPE_MINOR}, and {@code FREETYPE_PATCH}.
     *
     * @param library A source library handle.
     * @param amajor  The major version number.
     * @param aminor  The minor version number.
     * @param apatch  The patch version number.
     * @apiNote The reason why this function takes a library argument is because certain programs implement
     * library initialization in a custom way that doesn't use {@link #FTInitFreeType}. In such cases,
     * the library version might not be available before the library object has been created.
     */
    static void FTLibraryVersion(@In MemoryAddress library, @Out MemorySegment amajor, @Out MemorySegment aminor, @Out MemorySegment apatch) {
        try {
            VersionInternal.FT_LIBRARY_VERSION.invoke(library, amajor.address(), aminor.address(), apatch.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Initialize a new FreeType library object.
     * The set of modules that are registered by this function is determined at build time.
     *
     * @param alibrary A handle to a new library object.
     * @return FreeType error code. 0 means success.
     * @apiNote In case you want to provide your own memory allocating routines, use FTNewLibrary instead,
     * followed by a call to {@code FT_Add_Default_Modules} (or a series of calls to {@code FT_Add_Module})
     * and {@code FT_Set_Default_Properties}.<br/>
     * <p>
     * See the documentation of {@link FTLibrary} and {@link FTFace} for multi-threading issues.<br/>
     * <p>
     * If you need reference-counting (cf. {@code FT_Reference_Library}), use {@code FT_New_Library}
     * and {@code FT_Done_Library}.<br/>
     * <p>
     * If compilation option FT_CONFIG_OPTION_ENVIRONMENT_PROPERTIES is set, this function
     * reads the FREETYPE_PROPERTIES environment variable to control driver properties.
     */
    static int FTInitFreeType(@Out MemorySegment alibrary) {
        try {
            return (int) BaseInterface.FT_INIT_FREETYPE.invoke(alibrary.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }
}
