package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.struct.LongField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure used to store a 2x3 matrix.
 * Coefficients are in 16.16 fixed-point format. The computation performed is<br/>
 * <pre>
 *   x' = x*xx + y*xy + dx
 *   y' = x*yx + y*yy + dy
 * </pre>
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_Affine_23_
 *   {
 *     FT_Fixed  xx, xy, dx;
 *     FT_Fixed  yx, yy, dy;
 *   } FT_Affine23;
 * }</pre>
 */
public final class FTAffine23 {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * Matrix coefficient.
     */
    public static final LongField XX;

    /**
     * Matrix coefficient.
     */
    public static final LongField XY;

    /**
     * x translation.
     */
    public static final LongField DX;

    /**
     * Matrix coefficient.
     */
    public static final LongField YX;

    /**
     * Matrix coefficient.
     */
    public static final LongField YY;

    /**
     * y translation.
     */
    public static final LongField DY;

    static {
        LayoutBuilder builder = new LayoutBuilder("LLLLLL", new String[] {
                "xx", "xy", "dx", "yx", "yy", "dy"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        XX = builder.newLong("xx");
        XY = builder.newLong("xy");
        DX = builder.newLong("dx");
        YX = builder.newLong("yx");
        YY = builder.newLong("yy");
        DY = builder.newLong("dy");
    }
}
