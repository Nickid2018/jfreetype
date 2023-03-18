package io.github.mmc1234.jfreetype.image;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.LongField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure used to hold an outline's bounding box, i.e.,
 * the coordinates of its extrema in the horizontal and vertical directions.
 *
 * @apiNote The bounding box is specified with the coordinates of the lower left and the upper right corner.
 * In PostScript, those values are often called (llx,lly) and (urx,ury), respectively.<br/>
 * If {@link #Y_MIN} is negative, this value gives the glyph's descender. Otherwise, the glyph doesn't descend below the baseline.
 * Similarly, if {@link #Y_MAX} is positive, this value gives the glyph's ascender.<br/>
 * {@link #X_MIN} gives the horizontal distance from the glyph's origin to the left edge of the glyph's bounding box.
 * If {@link #X_MIN} is negative, the glyph extends to the left of the origin.
 * @implNote In freetype/ftimage.h
 * <pre>{@code
 *   typedef struct FT_BBox_
 *   {
 *     FT_Pos  xMin, yMin;
 *     FT_Pos  xMax, yMax;
 *   } FT_BBox;
 * }</pre>
 */
public final class FTBBox {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The horizontal minimum (left-most).
     */
    public static final LongField X_MIN;

    /**
     * The vertical minimum (bottom-most).
     */
    public static final LongField Y_MIN;

    /**
     * The horizontal maximum (right-most).
     */
    public static final LongField X_MAX;

    /**
     * The vertical maximum (top-most).
     */
    public static final LongField Y_MAX;

    static {
        LayoutBuilder builder = new LayoutBuilder("LLLL", new String[]{
                "xMin", "yMin", "xMax", "yMax"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        X_MIN = builder.newLong("xMin");
        Y_MIN = builder.newLong("yMin");
        X_MAX = builder.newLong("xMax");
        Y_MAX = builder.newLong("yMax");
    }
}
