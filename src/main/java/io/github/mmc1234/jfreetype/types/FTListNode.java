package io.github.mmc1234.jfreetype.types;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * A structure used to hold a single list element.
 *
 * @implNote In freetype/fttypes.h
 * <pre>{@code
 *   typedef struct FT_ListNodeRec_
 *   {
 *     FT_ListNode  prev;
 *     FT_ListNode  next;
 *     void*        data;
 *   } FT_ListNodeRec;
 *   typedef struct FT_ListNodeRec_*  FT_ListNode;
 * }</pre>
 */
public final class FTListNode {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The previous element in the list. NULL if first.
     */
    public static final VarHandle PREV;

    /**
     * The next element in the list. NULL if last.
     */
    public static final VarHandle NEXT;

    /**
     * A typeless pointer to the listed object.
     */
    public static final VarHandle DATA;

    static {
        LayoutBuilder builder = new LayoutBuilder("AAA", new String[] { "prev", "next", "data" });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PREV = builder.primitiveField("prev");
        NEXT = builder.primitiveField("next");
        DATA = builder.primitiveField("data");
    }
}
