package io.github.mmc1234.jfreetype.image;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

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
    public static final VarHandle X;

    /**
     * The vertical coordinate.
     */
    public static final VarHandle Y;

    static {
        LayoutBuilder builder = new LayoutBuilder("II", new String[]{"x", "y"});
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        X = builder.varHandle("x");
        Y = builder.varHandle("y");
    }
}
