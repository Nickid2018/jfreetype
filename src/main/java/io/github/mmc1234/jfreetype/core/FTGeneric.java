package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.AddressField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

/**
 * Client applications often need to associate their own data to a variety of FreeType core objects.
 * For example, a text layout API might want to associate a glyph cache to a given size object.
 * Some FreeType object contains a generic field, of type {@link FTGeneric}, which usage is left to client applications and font servers.<br/>
 * It can be used to store a pointer to client-specific data, as well as the newAddress of a ‘finalizer’ function,
 * which will be called by FreeType when the object is destroyed (for example, the previous client example would put the newAddress
 * of the glyph cache destructor in the finalizer field).
 *
 * @implNote In freetype/fttypes.h
 * <pre>{@code
 *   typedef struct  FT_Generic_
 *   {
 *     void*                 data;
 *     FT_Generic_Finalizer  finalizer;
 *   } FT_Generic;
 * }</pre>
 */
public final class FTGeneric {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * A typeless pointer to any client-specified data. This field is completely ignored by the FreeType library.
     * finalizer
     */
    public static final AddressField DATA;

    /**
     * A pointer to a ‘generic finalizer’ function, which will be called when the object is destroyed.
     * If this field is set to NULL, no code will be called.
     *
     * @implNote <pre>{@code
     *    typedef void (*FT_Generic_Finalizer)(void* object);
     *  }</pre>
     * Describe a function used to destroy the ‘client’ data of any FreeType object.
     * See the description of the {@link FTGeneric} type for details of usage.<br/>
     * Input: The newAddress of the FreeType object that is under finalization.
     * Its client data is accessed through its generic field.
     */
    public static final AddressField FINALIZER;

    static {
        LayoutBuilder builder = new LayoutBuilder("AA", new String[]{"data", "finalizer"});
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        DATA = builder.newAddress("data");
        FINALIZER = builder.newAddress("finalizer");
    }
}
