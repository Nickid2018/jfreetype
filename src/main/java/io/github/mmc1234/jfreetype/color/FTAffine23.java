package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

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
    public static final VarHandle XX;

    /**
     * Matrix coefficient.
     */
    public static final VarHandle XY;

    /**
     * x translation.
     */
    public static final VarHandle DX;

    /**
     * Matrix coefficient.
     */
    public static final VarHandle YX;

    /**
     * Matrix coefficient.
     */
    public static final VarHandle YY;

    /**
     * y translation.
     */
    public static final VarHandle DY;

    static {
        LayoutBuilder builder = new LayoutBuilder("LLLLLL", new String[] {
                "xx", "xy", "dx", "yx", "yy", "dy"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        XX = builder.primitiveField("xx");
        XY = builder.primitiveField("xy");
        DX = builder.primitiveField("dx");
        YX = builder.primitiveField("yx");
        YY = builder.primitiveField("yy");
        DY = builder.primitiveField("dy");
    }
}
