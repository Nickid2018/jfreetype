package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.LongField;
import io.github.mmc1234.jfreetype.util.ShortField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * This structure models the metrics of a bitmap strike (i.e., a set of glyphs for a given point size and resolution)
 * in a bitmap font. It is used for the {@link FTFace#AVAILABLE_SIZES}.
 *
 * @apiNote Windows FNT: The nominal size given in an FNT font is not reliable. If the driver finds it incorrect,
 * it sets size to some calculated values, and x_ppem and y_ppem to the pixel width and height given in the font,
 * respectively.<br/>
 * TrueType embedded bitmaps: size, width, and height values are not contained in the bitmap strike itself.
 * They are computed from the global font parameters.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct FT_Bitmap_Size_
 *   {
 *     FT_Short  height;
 *     FT_Short  width;
 *
 *     FT_Pos    size;
 *
 *     FT_Pos    x_ppem;
 *     FT_Pos    y_ppem;
 *   } FT_Bitmap_Size;
 * }</pre>
 */
public final class FTBitmapSize {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The vertical distance, in pixels, between two consecutive baselines. It is always positive.
     */
    public static final ShortField HEIGHT;

    /**
     * The average width, in pixels, of all glyphs in the strike.
     */
    public static final ShortField WIDTH;

    /**
     * The nominal size of the strike in 26.6 fractional points. This field is not very useful.
     */
    public static final LongField SIZE;

    /**
     * The horizontal ppem (nominal width) in 26.6 fractional pixels.
     */
    public static final LongField X_PPEM;

    /**
     * The vertical ppem (nominal height) in 26.6 fractional pixels.
     */
    public static final LongField Y_PPEM;

    static {
        LayoutBuilder builder = new LayoutBuilder("SSLLL", new String[]{
                "height", "width", "size", "x_ppem", "y_ppem"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        HEIGHT = builder.newShort("height");
        WIDTH = builder.newShort("width");
        SIZE = builder.newLong("size");
        X_PPEM = builder.newLong("x_ppem");
        Y_PPEM = builder.newLong("y_ppem");
    }
}
