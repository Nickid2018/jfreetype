package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.StructLayoutBuilder;
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
        StructLayoutBuilder builder = new StructLayoutBuilder("LL", new String[] { "x", "y" });
        STRUCT_LAYOUT = builder.getStructLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        X = builder.varHandle("x");
        Y = builder.varHandle("y");
    }
}
