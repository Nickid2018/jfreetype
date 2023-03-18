package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.struct.LongField;
import io.github.mmc1234.jfreetype.struct.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure representing a ‘COLR’ v1 PaintTranslate paint table. Used for translating downstream paints by a given x and y delta.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct  FT_PaintTranslate_
 *   {
 *     FT_OpaquePaint  paint;
 *
 *     FT_Fixed  dx;
 *     FT_Fixed  dy;
 *   } FT_PaintTranslate;
 * }</pre>
 */
public final class FTPaintTranslate {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * An {@link FTOpaquePaint} object referencing the paint that is to be rotated.
     */
    public static final StructField PAINT;

    /**
     * Translation in x direction in font units represented as a 16.16 fixed-point value.
     */
    public static final LongField DX;

    /**
     * Translation in y direction in font units represented as a 16.16 fixed-point value.
     */
    public static final LongField DY;

    static {
        LayoutBuilder builder = new LayoutBuilder("0LL", new String[] {
                "paint", "dx", "dy"
        }, FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PAINT = builder.newStruct("paint", FTOpaquePaint.STRUCT_LAYOUT);
        DX = builder.newLong("dx");
        DY = builder.newLong("dy");
    }
}
