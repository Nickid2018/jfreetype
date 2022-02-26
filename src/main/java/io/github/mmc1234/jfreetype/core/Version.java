package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.internal.VersionInternal;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.st;

public interface Version {
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
     * library initialization in a custom way that doesn't use {@link FreeType#FTInitFreeType}. In such cases,
     * the library version might not be available before the library object has been created.
     */
    static void FTLibraryVersion(@In MemoryAddress library, @Out MemorySegment amajor, @Out MemorySegment aminor, @Out MemorySegment apatch) {
        try {
            VersionInternal.FT_LIBRARY_VERSION.invoke(library, amajor.address(), aminor.address(), apatch.address());
        } catch (Throwable e) {
            throw st(e);
        }
    }
}
