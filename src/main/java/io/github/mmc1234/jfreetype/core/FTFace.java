package io.github.mmc1234.jfreetype.core;

/**
 * A handle to a typographic face object.
 * A face object models a given typeface, in a given style.
 *
 * @apiNote A face object also owns a single {@link FTGlyphSlot} object,
 * as well as one or more {@link FTSize} objects.<br/>
 * Use {@link FreeType#FTNewFace} or {@link FreeType#FTOpenFace} to create
 * a new face object from a given filepath or a custom input stream.<br/>
 * Use {@link FreeType#FTDoneFace} to destroy it (along with its slot and sizes).<br/>
 * An {@link FTFace} object can only be safely used from one thread at a time.
 * Similarly, creation and destruction of {@link FTFace}
 * with the same {@link FTLibrary} object can only be done from one thread at a time.
 * On the other hand, functions like {@link FreeType#FTLoadGlyph}
 * and its siblings are thread-safe and do not need the lock to be held as long as
 * the same {@link FTFace} object is not used from multiple threads at the same time.<br/>
 * Fields may be changed after a call to {@link FreeType#FTAttachFile} or {@link FreeType#FTAttachStream}.<br/>
 * For an OpenType variation font, the values of the following fields can change after a call to FT_Set_Var_Design_Coordinates
 * (and friends) if the font contains an ‘MVAR’ table: ascender, descender, height, underline_position, and underline_thickness.<br/>
 * Especially for TrueType fonts see also the documentation for {@link FTSizeMetrics}.
 *
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct  FT_FaceRec_
 *   {
 *     FT_Long           num_faces;
 *     FT_Long           face_index;
 *
 *     FT_Long           face_flags;
 *     FT_Long           style_flags;
 *
 *     FT_Long           num_glyphs;
 *
 *     FT_String*        family_name;
 *     FT_String*        style_name;
 *
 *     FT_Int            num_fixed_sizes;
 *     FT_Bitmap_Size*   available_sizes;
 *
 *     FT_Int            num_charmaps;
 *     FT_CharMap*       charmaps;
 *
 *     FT_Generic        generic;
 *
 *     // The following member variables (down to `underline_thickness`)
 *     // are only relevant to scalable outlines; cf. @FT_Bitmap_Size
 *     // for bitmap fonts.
 *     FT_BBox bbox;
 *
 *     FT_UShort units_per_EM;
 *     FT_Short ascender;
 *     FT_Short descender;
 *     FT_Short height;
 *
 *     FT_Short max_advance_width;
 *     FT_Short max_advance_height;
 *
 *     FT_Short underline_position;
 *     FT_Short underline_thickness;
 *
 *     FT_GlyphSlot glyph;
 *     FT_Size size;
 *     FT_CharMap charmap;
 *
 *     // @private begin
 *
 *     FT_Driver driver;
 *     FT_Memory memory;
 *     FT_Stream stream;
 *
 *     FT_ListRec sizes_list;
 *
 *     FT_Generic autohint;   // face-specific auto-hinter data
 *     void* extensions;      // unused
 *
 *     FT_Face_Internal internal;
 *
 *     // @private end
 *
 *   } FT_FaceRec;
 *   typedef struct FT_FaceRec_* FT_Face;
 * }</pre>
 */
public final class FTFace {

    static {

    }
}
