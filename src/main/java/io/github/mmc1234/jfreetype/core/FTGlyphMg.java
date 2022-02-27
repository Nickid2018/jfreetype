package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.internal.GlyphManagement;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

public interface FTGlyphMg {

    static int FTNewGlyph(@In MemoryAddress library, @In int format, @In MemorySegment aglyph) {
        try {
            return (int) GlyphManagement.FT_NEW_GLYPH.invoke(library, format, aglyph.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    static int FTGetGlyph(@In MemoryAddress slot, @In MemorySegment aglyph) {
        try {
            return (int) GlyphManagement.FT_GET_GLYPH.invoke(slot, aglyph.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }


    static int FTGlyphCopy(@In MemoryAddress source, @In MemoryAddress target) {
        try {
            return (int) GlyphManagement.FT_GLYPH_COPY.invoke(source, target);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    static int FTGlyphTransform(@In MemoryAddress glyph, @In MemorySegment matrix, @In MemorySegment delta) {
        try {
            return (int) GlyphManagement.FT_GLYPH_TRANSFORM.invoke(glyph, matrix.address(), delta.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    static void FTGlyphGetCBox(@In MemoryAddress glyph, @In int bbox_mode, @In MemoryAddress acbox) {
        try {
            GlyphManagement.FT_GLYPH_GET_C_BOX.invoke(glyph, bbox_mode, acbox);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }


    static int FTGlyphToBitmap(@In @Out MemorySegment the_glyph,
                               @In FTRenderMode render_mode,
                               @In MemoryAddress origin,
                               boolean destory) {
        try {
            return (int) GlyphManagement.FT_GLYPH_TO_BITMAP.invoke(the_glyph.address(),
                    render_mode.getAsInt(), origin, destory ? VarUtils.TRUE : VarUtils.FALSE);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }


    static void FTDoneGlyph(@In MemoryAddress glyph) {
        try {
            GlyphManagement.FT_DONE_GLYPH.invoke(glyph);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

}
