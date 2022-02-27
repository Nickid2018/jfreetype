package io.github.mmc1234.jfreetype.system;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * A union type used to store either a long or a pointer.
 * This is used to store a file descriptor or a FILE* in an input stream.
 *
 * @implNote In freetype/ftsystem.h
 * <pre>{@code
 *  typedef union FT_StreamDesc_
 *   {
 *     long   value;
 *     void*  pointer;
 *   } FT_StreamDesc;
 * }</pre>
 */
public final class FTStreamDesc {

    public static final MemoryLayout UNION_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    public static final VarHandle VALUE;
    public static final VarHandle POINTER;

    static {
        LayoutBuilder builder = new LayoutBuilder("IA", new String[] {
                "value", "pointer"
        }, true);
        UNION_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        VALUE = builder.varHandle("value");
        POINTER = builder.varHandle("pointer");
    }
}
