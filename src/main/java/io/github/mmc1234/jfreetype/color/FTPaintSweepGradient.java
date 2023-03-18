package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.image.FTVector;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.LongField;
import io.github.mmc1234.jfreetype.util.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure representing a PaintSweepGradient value of the ‘COLR’ v1 extensions,
 * see ‘https://github.com/googlefonts/colr-gradients-spec’.
 * The glyph layer filled with this paint is drawn filled with a sweep gradient from start_angle to end_angle.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintSweepGradient_
 *   {
 *     FT_ColorLine  colorline;
 *
 *     FT_Vector  center;
 *     FT_Fixed   start_angle;
 *     FT_Fixed   end_angle;
 *   } FT_PaintSweepGradient;
 * }</pre>
 */
public final class FTPaintSweepGradient {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The {@link FTColorLine} information for this paint, i.e., the list of color stops along the gradient.
     */
    public static final StructField COLORLINE;

    /**
     * The center of the sweep gradient in font units represented as a vector of 16.16 fixed-point values.
     */
    public static final StructField CENTER;

    /**
     * The start angle of the sweep gradient in 16.16 fixed-point format specifying degrees divided by 180.0
     * (as in the spec). Multiply by 180.0f to receive degrees value. Values are given counter-clockwise,
     * starting from the (positive) y axis.
     */
    public static final LongField START_ANGLE;

    /**
     * The end angle of the sweep gradient in 16.16 fixed-point format specifying degrees divided by 180.0
     * (as in the spec). Multiply by 180.0f to receive degrees value. Values are given counter-clockwise,
     * starting from the (positive) y axis.
     */
    public static final LongField END_ANGLE;

    static {
        LayoutBuilder builder = new LayoutBuilder("01LL", new String[] {
                "colorline", "center", "start_angle", "end_angle"
        }, FTColorLine.STRUCT_LAYOUT, FTVector.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        COLORLINE = builder.newStruct("colorline", FTColorLine.STRUCT_LAYOUT);
        CENTER = builder.newStruct("center", FTVector.STRUCT_LAYOUT);
        START_ANGLE = builder.newLong("start_angle");
        END_ANGLE = builder.newLong("end_angle");
    }
}
