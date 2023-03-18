package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.LongField;
import io.github.mmc1234.jfreetype.util.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure representing a ‘COLR’ v1 PaintSkew paint table.
 * Used for skewing or shearing downstream paints by a given center and angle.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintSkew_
 *   {
 *     FT_OpaquePaint  paint;
 *
 *     FT_Fixed  x_skew_angle;
 *     FT_Fixed  y_skew_angle;
 *
 *     FT_Fixed  center_x;
 *     FT_Fixed  center_y;
 *   } FT_PaintSkew;
 * }</pre>
 */
public final class FTPaintSkew {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * An {@link FTOpaquePaint} object referencing the paint that is to be skewed.
     */
    public static final StructField PAINT;

    /**
     * The skewing angle in x direction in degrees divided by 180.0 (as in the spec)
     * represented as a 16.16 fixed-point value. Multiply by 180.0f to receive degrees.
     */
    public static final LongField X_SKEW_ANGLE;

    /**
     * The skewing angle in y direction in degrees divided by 180.0 (as in the spec)
     * represented as a 16.16 fixed-point value. Multiply by 180.0f to receive degrees.
     */
    public static final LongField Y_SKEW_ANGLE;

    /**
     * The x coordinate of the pivot point of the skew in font units represented as a 16.16 fixed-point value.
     */
    public static final LongField CENTER_X;

    /**
     * The y coordinate of the pivot point of the skew in font units represented as a 16.16 fixed-point value.
     */
    public static final LongField CENTER_Y;

    static {
        LayoutBuilder builder = new LayoutBuilder("0LLLL", new String[] {
                "paint", "x_skew_angle", "y_skew_angle", "center_x", "center_y"
        }, FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PAINT = builder.newStruct("paint", FTOpaquePaint.STRUCT_LAYOUT);
        X_SKEW_ANGLE = builder.newLong("x_skew_angle");
        Y_SKEW_ANGLE = builder.newLong("y_skew_angle");
        CENTER_X = builder.newLong("center_x");
        CENTER_Y = builder.newLong("center_y");
    }
}
