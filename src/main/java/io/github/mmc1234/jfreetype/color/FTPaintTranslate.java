package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

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
    public static final MethodHandle PAINT;

    /**
     * Translation in x direction in font units represented as a 16.16 fixed-point value.
     */
    public static final VarHandle DX;

    /**
     * Translation in y direction in font units represented as a 16.16 fixed-point value.
     */
    public static final VarHandle DY;

    static {
        LayoutBuilder builder = new LayoutBuilder("0LL", new String[] {
                "paint", "dx", "dy"
        }, FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PAINT = builder.structField("paint");
        DX = builder.primitiveField("dx");
        DY = builder.primitiveField("dy");
    }
}
