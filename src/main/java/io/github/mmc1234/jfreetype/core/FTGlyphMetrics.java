package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.LongField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure to model the metrics of a single glyph. The values are expressed in 26.6 fractional pixel format;
 * if the flag {@link FTLoadFlags#FT_LOAD_NO_SCALE} has been used while loading the glyph, values are expressed in font units instead.
 *
 * @apiNote If not disabled with {@link FTLoadFlags#FT_LOAD_NO_HINTING}, the values represent dimensions of
 * the hinted glyph (in case hinting is applicable).<br/>
 * Stroking a glyph with an outside border does not increase horiAdvance or vertAdvance; you have to manually
 * adjust these values to account for the added width and height.<br/>
 * FreeType doesn't use the ‘VORG’ table data for CFF fonts because it doesn't have an interface to
 * quickly retrieve the glyph height. The y coordinate of the vertical origin can be simply computed as
 * vertBearingY + height after loading a glyph.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct  FT_Glyph_Metrics_
 *   {
 *     FT_Pos  width;
 *     FT_Pos  height;
 *
 *     FT_Pos  horiBearingX;
 *     FT_Pos  horiBearingY;
 *     FT_Pos  horiAdvance;
 *
 *     FT_Pos  vertBearingX;
 *     FT_Pos  vertBearingY;
 *     FT_Pos  vertAdvance;
 *
 *   } FT_Glyph_Metrics;
 * }</pre>
 */
public final class FTGlyphMetrics {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The glyph's width.
     */
    public static final LongField WIDTH;

    /**
     * The glyph's height.
     */
    public static final LongField HEIGHT;

    /**
     * Left side bearing for horizontal layout.
     */
    public static final LongField HORI_BEARING_X;

    /**
     * Top side bearing for horizontal layout.
     */
    public static final LongField HORI_BEARING_Y;

    /**
     * Advance width for horizontal layout.
     */
    public static final LongField HORI_ADVANCE;

    /**
     * Left side bearing for vertical layout.
     */
    public static final LongField VERT_BEARING_X;

    /**
     * Top side bearing for vertical layout. Larger positive values mean further below the vertical glyph origin.
     */
    public static final LongField VERT_BEARING_Y;

    /**
     * Advance height for vertical layout. Positive values mean the glyph has a positive advance downward.
     */
    public static final LongField VERT_ADVANCE;

    static {
        LayoutBuilder builder = new LayoutBuilder("LLLLLLLL", new String[]{
                "width", "height", "horiBearingX", "horiBearingY", "horiAdvance", "vertBearingX", "vertBearingY", "vertAdvance"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        WIDTH = builder.newLong("width");
        HEIGHT = builder.newLong("height");
        HORI_BEARING_X = builder.newLong("horiBearingX");
        HORI_BEARING_Y = builder.newLong("horiBearingY");
        HORI_ADVANCE = builder.newLong("horiAdvance");
        VERT_BEARING_X = builder.newLong("vertBearingX");
        VERT_BEARING_Y = builder.newLong("vertBearingY");
        VERT_ADVANCE = builder.newLong("vertAdvance");
    }
}
