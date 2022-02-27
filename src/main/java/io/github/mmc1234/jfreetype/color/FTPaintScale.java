package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

/**
 * A structure representing all of the ‘COLR’ v1 ‘PaintScale*’ paint tables. Used for scaling downstream
 * paints by a given x and y scale, with a given center. This structure is used for all ‘PaintScale*’
 * types that are part of specification; fields of this structure are filled accordingly. If there is a center,
 * the center values are set, otherwise they are set to the zero coordinate.
 * If the source font file has ‘PaintScaleUniform*’ set, the scale values are set accordingly to the same value.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintScale_
 *   {
 *     FT_OpaquePaint  paint;
 *
 *     FT_Fixed  scale_x;
 *     FT_Fixed  scale_y;
 *
 *     FT_Fixed  center_x;
 *     FT_Fixed  center_y;
 *   } FT_PaintScale;
 * }</pre>
 */
public final class FTPaintScale {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * An {@link FTOpaquePaint} object referencing the paint that is to be scaled.
     */
    public static final MethodHandle PAINT;

    /**
     * Scale factor in x direction represented as a 16.16 fixed-point value.
     */
    public static final VarHandle SCALE_X;

    /**
     * Scale factor in y direction represented as a 16.16 fixed-point value.
     */
    public static final VarHandle SCALE_Y;

    /**
     * x coordinate of center point to scale from represented as a 16.16 fixed-point value.
     */
    public static final VarHandle CENTER_X;

    /**
     * y coordinate of center point to scale from represented as a 16.16 fixed-point value.
     */
    public static final VarHandle CENTER_Y;

    static {
        LayoutBuilder builder = new LayoutBuilder("0LLLL", new String[] {
                "paint", "scale_x", "scale_y", "center_x", "center_y"
        }, FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PAINT = builder.structField("paint");
        SCALE_X = builder.primitiveField("scale_x");
        SCALE_Y = builder.primitiveField("scale_y");
        CENTER_X = builder.primitiveField("center_x");
        CENTER_Y = builder.primitiveField("center_y");
    }
}
