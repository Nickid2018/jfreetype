package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * A simple structure to pass more or less generic parameters to {@link FreeTypeFace#FTOpenFace}
 * and {@link FreeTypeFace#FTFaceProperties}.
 *
 * @apiNote The ID and function of parameters are driver-specific.
 * See section ‘Parameter Tags’ for more information.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct FT_Parameter_
 *   {
 *     FT_ULong    tag;
 *     FT_Pointer  data;
 *   } FT_Parameter;
 * }</pre>
 */
public final class FTParameter {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * A four-byte identification tag.
     */
    public static final VarHandle TAG;

    /**
     * A pointer to the parameter data.
     */
    public static final VarHandle DATA;

    static {
        LayoutBuilder builder = new LayoutBuilder("LA", new String[]{"tag", "data"});
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        TAG = builder.varHandle("tag");
        DATA = builder.varHandle("data");
    }
}
