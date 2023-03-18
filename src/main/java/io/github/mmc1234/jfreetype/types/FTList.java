package io.github.mmc1234.jfreetype.types;

import io.github.mmc1234.jfreetype.util.AddressField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure used to hold a simple doubly-linked list. These are used in many parts of FreeType.
 *
 * @implNote In freetype/fttypes.h
 * <pre>{@code
 *   typedef struct FT_ListRec_
 *   {
 *     FT_ListNode  head;
 *     FT_ListNode  tail;
 *   } FT_ListRec;
 *   typedef struct FT_ListRec_* FT_List;
 * }</pre>
 */
public final class FTList {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The head (first element) of doubly-linked list.
     */
    public static final AddressField HEAD;

    /**
     * The tail (last element) of doubly-linked list.
     */
    public static final AddressField TAIL;

    static {
        LayoutBuilder builder = new LayoutBuilder("AA", new String[] { "head", "tail" });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        HEAD = builder.newAddress("head");
        TAIL = builder.newAddress("tail");
    }
}
