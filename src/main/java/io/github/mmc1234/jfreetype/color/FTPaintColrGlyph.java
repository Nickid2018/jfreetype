package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.struct.IntField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure representing a ‘COLR’ v1 PaintColorGlyph paint table.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct  FT_PaintColrGlyph_
 *   {
 *     FT_UInt  glyphID;
 *   } FT_PaintColrGlyph;
 * }</pre>
 */
public final class FTPaintColrGlyph {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The glyph ID from the BaseGlyphV1List table that is drawn for this paint.
     */
    public static final IntField GLYPH_ID;

    static {
        LayoutBuilder builder = new LayoutBuilder("I", new String[] { "glyphID" });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        GLYPH_ID = builder.newInt("glyphID");
    }
}
