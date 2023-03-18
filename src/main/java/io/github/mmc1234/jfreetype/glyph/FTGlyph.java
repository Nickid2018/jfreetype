package io.github.mmc1234.jfreetype.glyph;

import io.github.mmc1234.jfreetype.image.FTVector;
import io.github.mmc1234.jfreetype.struct.AddressField;
import io.github.mmc1234.jfreetype.struct.IntField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.struct.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * The root glyph structure contains a given glyph image plus its advance width in 16.16 fixed-point format.
 *
 * @implNote In ftglyph.h
 * <pre>{@code
 *   typedef struct FT_GlyphRec_
 *   {
 *     FT_Library             library;
 *     const FT_Glyph_Class*  clazz;
 *     FT_Glyph_Format        format;
 *     FT_Vector              advance;
 *   } FT_GlyphRec;
 *   typedef struct FT_GlyphRec_* FT_Glyph;
 * }</pre>
 */
public final class FTGlyph {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * A handle to the FreeType library object.
     */
    public static final AddressField LIBRARY;

    /**
     * A pointer to the glyph's class. Private.
     */
    public static final AddressField CLAZZ;

    /**
     * The format of the glyph's image.
     */
    public static final IntField FORMAT;

    /**
     * A 16.16 vector that gives the glyph's advance width.
     */
    public static final StructField ADVANCE;

    static {
        LayoutBuilder builder = new LayoutBuilder("AAI0", new String[] {
                "library", "clazz", "format", "advance"
        }, FTVector.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        LIBRARY = builder.newAddress("library");
        CLAZZ = builder.newAddress("clazz");
        FORMAT = builder.newInt("format");
        ADVANCE = builder.newStruct("advance", FTVector.STRUCT_LAYOUT);
    }
}
