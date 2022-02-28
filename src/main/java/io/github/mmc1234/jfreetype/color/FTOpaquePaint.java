package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * A structure representing an offset to a Paint value stored in any of the paint tables of a ‘COLR’ v1 font.
 * Compare Offset<24> there. When ‘COLR’ v1 paint tables represented by FreeType objects such as
 * FT_PaintColrLayers, FT_PaintComposite, or FT_PaintTransform reference downstream nested paint tables,
 * we do not immediately retrieve them but encapsulate their location in this type. Use FT_Get_Paint
 * to retrieve the actual FT_COLR_Paint object that describes the details of the respective paint table.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_Opaque_Paint_
 *   {
 *     FT_Byte*  p;
 *     FT_Bool   insert_root_transform;
 *   } FT_OpaquePaint;
 * }</pre>
 */
public final class FTOpaquePaint {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * An internal offset to a Paint table, needs to be set to NULL before passing this struct
     * as an argument to FT_Get_Paint.
     */
    public static final VarHandle P;

    /**
     * An internal boolean to track whether an initial root transform is to be provided. Do not set this value.
     */
    public static final VarHandle INSERT_ROOT_TRANSFORM;

    static {
        LayoutBuilder builder = new LayoutBuilder("AZ", new String[] { "p", "insert_root_transform" });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        P = builder.primitiveField("p");
        INSERT_ROOT_TRANSFORM = builder.primitiveField("insert_root_transform");
    }
}
