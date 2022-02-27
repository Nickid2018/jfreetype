package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Struct;
import io.github.mmc1234.jfreetype.internal.GlyphColorManagement;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

public interface FTGlyphColorMg {
    static int FTPaletteDataGet(@In MemoryAddress face, @In MemorySegment apalette) {
        try {
            return (int) GlyphColorManagement.FT_PALETTE_DATA_GET.invoke(face, apalette.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    static int FTPaletteSelect(@In MemoryAddress face, short palette_index, @In MemorySegment apalette) {
        try {
            return (int) GlyphColorManagement.FT_PALETTE_SELECT.invoke(face, palette_index, apalette.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }


    static int FTPaletteSetForegroundColor(@In MemoryAddress face, @In @Struct MemorySegment foregroundColor) {
        try {
            return (int) GlyphColorManagement.FT_PALETTE_SELECT.invoke(face, foregroundColor.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }
}
