package com.github.mmc1234.jfreetype;

import com.github.mmc1234.jfreetype.util.StructLayoutBuilder;
import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;

/**
 * Client applications often need to associate their own data to a variety of FreeType core objects.
 * For example, a text layout API might want to associate a glyph cache to a given size object.
 * Some FreeType object contains a generic field, of type {@link FTGeneric}, which usage is left to client applications and font servers.<br/>
 * It can be used to store a pointer to client-specific data, as well as the address of a ‘finalizer’ function,
 * which will be called by FreeType when the object is destroyed (for example, the previous client example would put the address
 * of the glyph cache destructor in the finalizer field).
 */
public final class FTGeneric {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * A typeless pointer to any client-specified data. This field is completely ignored by the FreeType library.
     * finalizer
     */
    public static final VarHandle DATA;

    /**
     *  A pointer to a ‘generic finalizer’ function, which will be called when the object is destroyed.
     *  If this field is set to NULL, no code will be called.
     */
    public static final VarHandle FINALIZER;

    static {
        StructLayoutBuilder builder = new StructLayoutBuilder("AA", new String[]{ "data", "finalizer" });
        STRUCT_LAYOUT = builder.getStructLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        DATA = builder.varHandle("data");
        FINALIZER = builder.varHandle("finalizer");
    }
}
