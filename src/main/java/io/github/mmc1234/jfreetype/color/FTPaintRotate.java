package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.struct.LongField;
import io.github.mmc1234.jfreetype.struct.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure representing a ‘COLR’ v1 PaintRotate paint table. Used for rotating downstream paints with a given center and angle.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintRotate_
 *   {
 *     FT_OpaquePaint  paint;
 *
 *     FT_Fixed  angle;
 *
 *     FT_Fixed  center_x;
 *     FT_Fixed  center_y;
 *   } FT_PaintRotate;
 * }</pre>
 */
public final class FTPaintRotate {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * An {@link FTOpaquePaint} object referencing the paint that is to be rotated.
     */
    public static final StructField PAINT;

    /**
     * The rotation angle that is to be applied in degrees divided by 180.0 (as in the spec) represented
     * as a 16.16 fixed-point value. Multiply by 180.0f to receive degrees value.
     */
    public static final LongField ANGLE;

    /**
     * The x coordinate of the pivot point of the rotation in font units) represented as a 16.16 fixed-point value.
     */
    public static final LongField CENTER_X;

    /**
     * The y coordinate of the pivot point of the rotation in font units represented as a 16.16 fixed-point value.
     */
    public static final LongField CENTER_Y;

    static {
        LayoutBuilder builder = new LayoutBuilder("0LLL", new String[] {
                "paint", "angle", "center_x", "center_y"
        }, FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PAINT = builder.newStruct("paint", FTOpaquePaint.STRUCT_LAYOUT);
        ANGLE = builder.newLong("angle");
        CENTER_X = builder.newLong("center_x");
        CENTER_Y = builder.newLong("center_y");
    }
}
