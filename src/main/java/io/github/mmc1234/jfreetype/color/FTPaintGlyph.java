package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.IntField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.StructField;
import jdk.incubator.foreign.MemoryLayout;

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
    public static final StructField PAINT;

    /**
     * The glyph ID from the ‘glyf’ table, which serves as the contour information that is filled with paint.
     */
    public static final IntField GLYPH_ID;

    static {
        LayoutBuilder builder = new LayoutBuilder("0I", new String[] { "paint", "glyphID" },
                FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PAINT = builder.newStruct("paint", FTOpaquePaint.STRUCT_LAYOUT);
        GLYPH_ID = builder.newInt("glyphID");
    }
}
