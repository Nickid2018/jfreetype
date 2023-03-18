package io.github.mmc1234.jfreetype.image;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.LongField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A simple structure used to store a 2D vector; coordinates are of the FT_Pos(long) type.
 *
 * @implNote In freetype/ftimage.h
 * <pre>{@code
 *   typedef struct FT_Vector_
 *   {
 *     FT_Pos x;
 *     FT_Pos y;
 *   } FT_Vector;
 * }</pre>
 */
public final class FTVector {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The horizontal coordinate.
     */
    public static final LongField X;

    /**
     * The vertical coordinate.
     */
    public static final LongField Y;

    static {
        LayoutBuilder builder = new LayoutBuilder("LL", new String[]{"x", "y"});
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        X = builder.newLong("x");
        Y = builder.newLong("y");
    }
}
