package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * This iterator object is needed for {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGetColorlineStops}.
 * It keeps state while iterating over the stops of an FTColorLine, representing the ColorLine struct of
 * the v1 extensions to ‘COLR’, see ‘https://github.com/googlefonts/colr-gradients-spec’.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_ColorStopIterator_
 *   {
 *     FT_UInt  num_color_stops;
 *     FT_UInt  current_color_stop;
 *
 *     FT_Byte*  p;
 *   } FT_ColorStopIterator;
 * }</pre>
 */
public final class FTColorStopIterator {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The number of color stops for the requested glyph index. Set by
     * {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGetColorlineStops}.
     */
    public static final VarHandle NUM_COLOR_STOPS;

    /**
     * The current color stop. Set by {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGetColorlineStops}.
     */
    public static final VarHandle CURRENT_COLOR_STOP;

    /**
     * An opaque pointer into ‘COLR’ table data. The caller must set this to NULL before the first call of
     * {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGetColorlineStops}.
     */
    public static final VarHandle P;

    static {
        LayoutBuilder builder = new LayoutBuilder("IIA", new String[] {
                "num_color_stops", "current_color_stop", "p"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        NUM_COLOR_STOPS = builder.primitiveField("num_color_stops");
        CURRENT_COLOR_STOP = builder.primitiveField("current_color_stop");
        P = builder.primitiveField("p");
    }
}
