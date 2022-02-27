package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.Struct;
import io.github.mmc1234.jfreetype.internal.GlyphLayerManagement;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

public interface FTGlyphLayerMg {

    static int FTGetColorGlyphLayer(@In MemoryAddress face,
                                    @In int base_glyph,
                                    @Out MemorySegment aglyph_index,
                                    @Out MemorySegment acolor_index,
                                    @In MemoryAddress iterator) {
        try {
            return (int) GlyphLayerManagement.FT_GET_COLOR_GLYPH_LAYER
                    .invoke(face, base_glyph, aglyph_index.address(), acolor_index.address(), iterator);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    static boolean FTGetColorGlyphPaint(@In MemoryAddress face,
                                    @In int base_glyph,
                                    @Out FTColorRootTransform root_transform,
                                    @In MemoryAddress paint) {
        try {
            return VarUtils.TRUE == (int) GlyphLayerManagement.FT_GET_COLOR_GLYPH_PAINT
                    .invoke(face, base_glyph, root_transform.getAsInt(), paint);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }


    static boolean FTGetColorGlyphClipBox(@In MemoryAddress face,
                                        @In int base_glyph,
                                        @In MemoryAddress clip_box) {
        try {
            return VarUtils.TRUE == (int) GlyphLayerManagement.FT_GET_COLOR_GLYPH_CLIP_BOX
                    .invoke(face, base_glyph, clip_box);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    static boolean FTGetPaintLayers(@In MemoryAddress face,
                                              @In MemoryAddress iterator,
                                              @In MemoryAddress paint) {
        try {
            return VarUtils.TRUE == (int) GlyphLayerManagement.FT_GET_PAINT_LAYERS
                    .invoke(face, iterator, paint);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    static boolean FTGetColorlineStops(@In MemoryAddress face,
                                    @In MemoryAddress color_stop,
                                    @In MemoryAddress iterator) {
        try {
            return VarUtils.TRUE == (int) GlyphLayerManagement.FT_GET_COLOR_LINE_STOPS
                    .invoke(face, color_stop, iterator);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }


    static boolean FTGetPaint(@In MemoryAddress face,
                                       @In @Struct MemorySegment opaque_paint,
                                       @In MemoryAddress paint) {
        try {
            return VarUtils.TRUE == (int) GlyphLayerManagement.FT_GET_PAINT
                    .invoke(face, opaque_paint.address(), paint);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }
}
