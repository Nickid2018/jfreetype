package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

/**
 * A structure representing a ‘COLR’ v1 PaintGlyph paint table.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintGlyph_
 *   {
 *     FT_OpaquePaint  paint;
 *     FT_UInt         glyphID;
 *   } FT_PaintGlyph;
 * }</pre>
 */
public final class FTPaintGlyph {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * An opaque paint object pointing to a Paint table that serves as the fill for the glyph ID.
     */
    public static final MethodHandle PAINT;

    /**
     * The glyph ID from the ‘glyf’ table, which serves as the contour information that is filled with paint.
     */
    public static final VarHandle GLYPH_ID;

    static {
        LayoutBuilder builder = new LayoutBuilder("0I", new String[] { "paint", "glyphID" },
                FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PAINT = builder.structField("paint");
        GLYPH_ID = builder.primitiveField("glyphID");
    }
}
