package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.image.FTBBox;
import io.github.mmc1234.jfreetype.struct.*;
import io.github.mmc1234.jfreetype.types.FTList;
import io.github.mmc1234.jfreetype.util.*;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;

/**
 * A handle to a typographic face object.
 * A face object models a given typeface, in a given style.
 *
 * @apiNote A face object also owns a single {@link FTGlyphSlot} object,
 * as well as one or more {@link FTSize} objects.<br/>
 * Use {@link FreeTypeFace#FTNewFace} or {@link FreeTypeFace#FTOpenFace} to create
 * a new face object from a given filepath or a custom input stream.<br/>
 * Use {@link FreeTypeFace#FTDoneFace} to destroy it (along with its slot and sizes).<br/>
 * An {@link FTFace} object can only be safely used from one thread at a time.
 * Similarly, creation and destruction of {@link FTFace}
 * with the same {@link FTLibrary} object can only be done from one thread at a time.
 * On the other hand, functions like {@link FreeTypeGlyph#FTLoadGlyph}
 * and its siblings are thread-safe and do not need the lock to be held as long as
 * the same {@link FTFace} object is not used from multiple threads at the same time.<br/>
 * Fields may be changed after a call to {@link FreeTypeFace#FTAttachFile} or {@link FreeTypeFace#FTAttachStream}.<br/>
 * For an OpenType variation font, the values of the following fields can change after a call to FT_Set_Var_Design_Coordinates
 * (and friends) if the font contains an ‘MVAR’ table: ascender, descender, height, underline_position, and underline_thickness.<br/>
 * Especially for TrueType fonts see also the documentation for {@link FTSizeMetrics}.
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
 *     // are only relevant to scalable outlines; cf. @FTBitmapSize
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

    // --- Face Flags

    /**
     * The face contains outline glyphs. Note that a face can contain bitmap strikes also, i.e.,
     * a face can have both this flag and {@link #FT_FACE_FLAG_FIXED_SIZES} set.
     */
    public static final long FT_FACE_FLAG_SCALABLE = 1L;

    /**
     * The face contains bitmap strikes. See also the {@link #NUM_FIXED_SIZES} and {@link #AVAILABLE_SIZES}.
     */
    public static final long FT_FACE_FLAG_FIXED_SIZES = 1L << 1;

    /**
     * The face contains fixed-width characters (like Courier, Lucida, MonoType, etc.).
     */
    public static final long FT_FACE_FLAG_FIXED_WIDTH = 1L << 2;

    /**
     * The face uses the SFNT storage scheme. For now, this means TrueType and OpenType.
     */
    public static final long FT_FACE_FLAG_SFNT = 1L << 3;

    /**
     * The face contains horizontal glyph metrics. This should be set for all common formats.
     */
    public static final long FT_FACE_FLAG_HORIZONTAL = 1L << 4;

    /**
     * The face contains vertical glyph metrics. This is only available in some formats, not all of them.
     */
    public static final long FT_FACE_FLAG_VERTICAL = 1L << 5;

    /**
     * The face contains kerning information. If set, the kerning distance can be retrieved using {@link FreeTypeFace#FTGetKerning}.
     * Otherwise the function always return the vector (0,0).
     * Note that FreeType doesn't handle kerning data from the SFNT ‘GPOS’ table (as present in many OpenType fonts).
     */
    public static final long FT_FACE_FLAG_KERNING = 1L << 6;

    /**
     * THIS FLAG IS DEPRECATED. DO NOT USE OR TEST IT.
     */
    public static final long FT_FACE_FLAG_FAST_GLYPHS = 1L << 7;

    /**
     * The face contains multiple masters and is capable of interpolating between them.
     * Supported formats are Adobe MM, TrueType GX, and OpenType variation fonts.<br/>
     * See section ‘Multiple Masters’ for API details.
     */
    public static final long FT_FACE_FLAG_MULTIPLE_MASTERS = 1L << 8;

    /**
     * The face contains glyph names, which can be retrieved using {@link FreeTypeGlyph#FTGetGlyphName}.
     * Note that some TrueType fonts contain broken glyph name tables. Use the function FT_Has_PS_Glyph_Names when needed.
     */
    public static final long FT_FACE_FLAG_GLYPH_NAMES = 1L << 9;

    /**
     * Used internally by FreeType to indicate that a face's stream was provided by the client application and should not be destroyed
     * when {@link FreeTypeFace#FTDoneFace} is called. Don't read or test this flag.
     */
    public static final long FT_FACE_FLAG_EXTERNAL_STREAM = 1L << 10;

    /**
     * The font driver has a hinting machine of its own. For example, with TrueType fonts, it makes sense to use data from
     * the SFNT ‘gasp’ table only if the native TrueType hinting engine (with the bytecode interpreter)
     * is available and active.
     */
    public static final long FT_FACE_FLAG_HINTER = 1L << 11;

    /**
     * The face is CID-keyed. In that case, the face is not accessed by glyph indices but by CID values.
     * For subset CID-keyed fonts this has the consequence that not all index values are a valid argument
     * to {@link FreeTypeGlyph#FTLoadGlyph}. Only the CID values for which corresponding glyphs in the subset font
     * exist make {@link FreeTypeGlyph#FTLoadGlyph} return successfully; in all other cases you get an
     * FT_Err_Invalid_Argument error.<br/>
     * Note that CID-keyed fonts that are in an SFNT wrapper (this is, all OpenType/CFF fonts)
     * don't have this flag set since the glyphs are accessed in the normal way (using contiguous indices);
     * the ‘CID-ness’ isn't visible to the application.
     */
    public static final long FT_FACE_FLAG_CID_KEYED = 1L << 12;

    /**
     * The face is ‘tricky’, this is, it always needs the font format's native hinting engine to get
     * a reasonable result. A typical example is the old Chinese font mingli.ttf (but not mingliu.ttc)
     * that uses TrueType bytecode instructions to move and scale all of its subglyphs.<br/>
     * It is not possible to auto-hint such fonts using {@link FreeType#FT_LOAD_FORCE_AUTOHINT};
     * it will also ignore {@link FreeType#FT_LOAD_NO_HINTING}. You have to set both {@link FreeType#FT_LOAD_NO_HINTING}
     * and {@link FreeType#FT_LOAD_NO_AUTOHINT} to really disable hinting; however, you probably never want this
     * except for demonstration purposes.<br/>
     * Currently, there are about a dozen TrueType fonts in the list of tricky fonts;
     * they are hard-coded in file ttobjs.c.
     */
    public static final long FT_FACE_FLAG_TRICKY = 1L << 13;

    /**
     * [Since 2.5.1] The face has color glyph tables. See {@link FreeType#FT_LOAD_COLOR} for more information.
     */
    public static final long FT_FACE_FLAG_COLOR = 1L << 14;

    /**
     * [Since 2.9] Set if the current face (or named instance) has been altered with FT_Set_MM_Design_Coordinates,
     * FT_Set_Var_Design_Coordinates, or FT_Set_Var_Blend_Coordinates.
     * This flag is unset by a call to FT_Set_Named_Instance.
     */
    public static final long FT_FACE_FLAG_VARIATION = 1L << 15;

    // --- Style flags

    /**
     * The face style is italic or oblique.
     */
    public static final int FT_STYLE_FLAG_ITALIC = 1;

    /**
     * The face is bold.
     */
    public static final int FT_STYLE_FLAG_BOLD = 1 << 1;

    // Style flags Note:
    // The style information as provided by FreeType is very basic. More details are beyond the scope
    // and should be done on a higher level (for example, by analyzing various fields of the ‘OS/2’ table
    // in SFNT based fonts).
    // --- Note End

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The number of faces in the font file. Some font formats can have multiple faces in a single font file.
     */
    public static final LongField NUM_FACES;

    /**
     * This field holds two different values. Bits 0-15 are the index of the face in the font file (starting with value 0).
     * They are set to 0 if there is only one face in the font file.<br/>
     * [Since 2.6.1] Bits 16-30 are relevant to GX and OpenType variation fonts only,
     * holding the named instance index for the current face index (starting with value 1;
     * value 0 indicates font access without a named instance). For non-variation fonts, bits 16-30 are ignored.
     * If we have the third named instance of face 4, say, face_index is set to 0x00030004.<br/>
     * Bit 31 is always zero (this is, face_index is always a positive value).<br/>
     * [Since 2.9] Changing the design coordinates with FT_Set_Var_Design_Coordinates or
     * FT_Set_Var_Blend_Coordinates does not influence the named instance index value
     * (only FT_Set_Named_Instance does that).
     */
    public static final LongField FACE_INDEX;

    /**
     * A set of bit flags that give important information about the face; see FT_FACE_FLAG_XXX for the details.
     */
    public static final LongField FACE_FLAGS;

    /**
     * The lower 16 bits contain a set of bit flags indicating the style of the face;
     * see FT_STYLE_FLAG_XXX for the details.<br/>
     * [Since 2.6.1] Bits 16-30 hold the number of named instances available for the current face
     * if we have a GX or OpenType variation (sub)font. Bit 31 is always zero
     * (this is, style_flags is always a positive value).
     * Note that a variation font has always at least one named instance, namely the default instance.
     */
    public static final LongField STYLE_FLAGS;

    /**
     * The number of glyphs in the face. If the face is scalable and has sbits (see {@link #NUM_FIXED_SIZES}),
     * it is set to the number of outline glyphs.<br/>
     * For CID-keyed fonts (not in an SFNT wrapper) this value gives the highest CID used in the font.
     */
    public static final LongField NUM_GLYPHS;

    /**
     * The face's family name. This is an ASCII string, usually in English, that describes the typeface's family
     * (like ‘Times New Roman’, ‘Bodoni’, ‘Garamond’, etc). This is a least common denominator used to list fonts.
     * Some formats (TrueType & OpenType) provide localized and Unicode versions of this string.
     * Applications should use the format-specific interface to access them. Can be NULL
     * (e.g., in fonts embedded in a PDF file).<br/>
     * In case the font doesn't provide a specific family name entry, FreeType tries to synthesize one,
     * deriving it from other name entries.
     */
    public static final AddressField FAMILY_NAME;

    /**
     * The face's style name. This is an ASCII string, usually in English, that describes the typeface's style
     * (like ‘Italic’, ‘Bold’, ‘Condensed’, etc). Not all font formats provide a style name, so this field is optional,
     * and can be set to NULL. As for {@link #FAMILY_NAME}, some formats provide localized and Unicode versions of this string.
     * Applications should use the format-specific interface to access them.
     */
    public static final AddressField STYLE_NAME;

    /**
     * The number of bitmap strikes in the face. Even if the face is scalable,
     * there might still be bitmap strikes, which are called ‘sbits’ in that case.
     */
    public static final IntField NUM_FIXED_SIZES;

    /**
     * An array of {@link FTBitmapSize} for all bitmap strikes in the face.
     * It is set to NULL if there is no bitmap strike.<br/>
     * Note that FreeType tries to sanitize the strike data since they are sometimes sloppy or incorrect,
     * but this can easily fail.
     */
    public static final AddressField AVAILABLE_SIZES;

    /**
     * The number of charmaps in the face.
     */
    public static final IntField NUM_CHARMAPS;

    /**
     * An array of the charmaps of the face.
     */
    public static final AddressField CHARMAPS;

    /**
     * A field reserved for client uses. See the {@link FTGeneric} type description.
     */
    public static final StructField GENERIC;

    /**
     * The font bounding box. Coordinates are expressed in font units (see {@link #UNITS_PER_EM}).
     * The box is large enough to contain any glyph from the font. Thus, bbox.yMax can be seen
     * as the ‘maximum ascender’, and bbox.yMin as the ‘minimum descender’. Only relevant for scalable formats.<br/>
     * Note that the bounding box might be off by (at least) one pixel for hinted fonts.
     * See {@link FTSizeMetrics} for further discussion.<br/>
     * Note that the bounding box does not vary in OpenType variable fonts and should only be used
     * in relation to the default instance.
     */
    public static final StructField BBOX;

    /**
     * The number of font units per EM square for this face. This is typically 2048 for TrueType fonts,
     * and 1000 for Type 1 fonts. Only relevant for scalable formats.
     */
    public static final ShortField UNITS_PER_EM;

    /**
     * The typographic ascender of the face, expressed in font units.
     * For font formats not having this information, it is set to {@link FTBBox#Y_MAX}. Only relevant for scalable formats.
     */
    public static final ShortField ASCENDER;

    /**
     * The typographic descender of the face, expressed in font units. For font formats not having this information,
     * it is set to {@link FTBBox#Y_MIN}. Note that this field is negative for values below the baseline.
     * Only relevant for scalable formats.
     */
    public static final ShortField DESCENDER;

    /**
     * This value is the vertical distance between two consecutive baselines, expressed in font units.
     * It is always positive. Only relevant for scalable formats.<br/>
     * If you want the global glyph height, use ascender - descender.
     */
    public static final ShortField HEIGHT;

    /**
     * The maximum advance width, in font units, for all glyphs in this face.
     * This can be used to make word wrapping computations faster. Only relevant for scalable formats.
     */
    public static final ShortField MAX_ADVANCE_WIDTH;

    /**
     * The maximum advance height, in font units, for all glyphs in this face. This is only relevant for vertical layouts,
     * and is set to height for fonts that do not provide vertical metrics. Only relevant for scalable formats.
     */
    public static final ShortField MAX_ADVANCE_HEIGHT;

    /**
     * The position, in font units, of the underline line for this face.
     * It is the center of the underlining stem. Only relevant for scalable formats.
     */
    public static final ShortField UNDERLINE_POSITION;

    /**
     * The thickness, in font units, of the underline for this face. Only relevant for scalable formats.
     */
    public static final ShortField UNDERLINE_THICKNESS;

    /**
     * The face's associated glyph slot(s).
     */
    public static final AddressField GLYPH;

    /**
     * The current active size for this face.
     */
    public static final AddressField SIZE;

    /**
     * The current active charmap for this face.
     */
    public static final AddressField CHARMAP;

    // --- Private Start

    public static final AddressField DRIVER;
    public static final AddressField MEMORY;
    public static final AddressField STREAM;
    public static final StructField SIZES_LIST;
    public static final StructField AUTOHINT;
    public static final AddressField EXTENSIONS;
    public static final AddressField INTERNAL;

    // --- Private End

    // --- Macros Start

    /**
     * A macro that returns true whenever a face object contains horizontal metrics
     * (this is true for all font formats though).
     *
     * @implNote
     * <pre>{@code
     * #define FT_HAS_HORIZONTAL( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_HORIZONTAL ) )
     * }</pre>
     */
    public static boolean FTHasHorizontal(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_HORIZONTAL) != 0;
        //return (VarUtils.getInt(FACE_FLAGS, face) & FT_FACE_FLAG_HORIZONTAL) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains real vertical metrics (and not only synthesized ones).
     *
     * @implNote
     * <pre>{@code
     * #define FT_HAS_VERTICAL( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_VERTICAL ) )
     * }</pre>
     */
    public static boolean FTHasVertical(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_VERTICAL) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains kerning data that can be accessed with
     * {@link FreeTypeFace#FTGetKerning}.
     *
     * @implNote
     * <pre>{@code
     * #define FT_HAS_KERNING( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_KERNING ) )
     * }</pre>
     */
    public static boolean FTHasKerning(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_KERNING) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains some embedded bitmaps. See {@link #AVAILABLE_SIZES}.
     *
     * @implNote
     * <pre>{@code
     * #define FT_HAS_FIXED_SIZES( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_FIXED_SIZES ) )
     * }</pre>
     */
    public static boolean FTHasFixedSizes(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_FIXED_SIZES) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains some glyph names that can be accessed
     * through {@link FreeTypeGlyph#FTGetGlyphName}.
     *
     * @implNote
     * <pre>{@code
     * #define FT_HAS_GLYPH_NAMES( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_GLYPH_NAMES ) )
     * }</pre>
     */
    public static boolean FTHasGlyphNames(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_GLYPH_NAMES) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains tables for color glyphs.
     *
     * @implNote
     * <pre>{@code
     * #define FT_HAS_COLOR( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_COLOR ) )
     * }</pre>
     */
    public static boolean FTHasColor(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_COLOR) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains some multiple masters. The functions provided by
     * {@code FT_MULTIPLE_MASTERS_H} are then available to choose the exact design you want.
     *
     * @implNote
     * <pre>{@code
     * #define FT_HAS_MULTIPLE_MASTERS( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_MULTIPLE_MASTERS ) )
     * }</pre>
     */
    public static boolean FTHasMultipleMasters(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_MULTIPLE_MASTERS) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains a font whose format is based on the SFNT storage scheme.
     * This usually means: TrueType fonts, OpenType fonts, as well as SFNT-based embedded bitmap fonts.<br/>
     * If this macro is true, all functions defined in {@code FT_SFNT_NAMES_H} and {@code FT_TRUETYPE_TABLES_H} are available.
     *
     * @implNote
     * <pre>{@code
     * #define FT_IS_SFNT( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_SFNT ) )
     * }</pre>
     */
    public static boolean FTIsSFNT(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_SFNT) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains a scalable font face
     * (true for TrueType, Type 1, Type 42, CID, OpenType/CFF, and PFR font formats).
     *
     * @implNote
     * <pre>{@code
     * #define FT_IS_SCALABLE( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_SCALABLE ) )
     * }</pre>
     */
    public static boolean FTIsScalable(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_SCALABLE) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains a font face
     * that contains fixed-width (or ‘monospace’, ‘fixed-pitch’, etc.) glyphs.
     *
     * @implNote
     * <pre>{@code
     * #define FT_IS_FIXED_WIDTH( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_FIXED_WIDTH ) )
     * }</pre>
     */
    public static boolean FTIsFixedWidth(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_FIXED_WIDTH) != 0;
    }

    /**
     * A macro that returns true whenever a face object contains a CID-keyed font.
     * See the discussion of {@link #FT_FACE_FLAG_CID_KEYED} for more details.<br/>
     * If this macro is true, all functions defined in {@code FT_CID_H} are available.
     *
     * @implNote
     * <pre>{@code
     * #define FT_IS_CID_KEYED( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_CID_KEYED ) )
     * }</pre>
     */
    public static boolean FTIsCIDKeyed(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_CID_KEYED) != 0;
    }

    /**
     * A macro that returns true whenever a face represents a ‘tricky’ font.
     * See the discussion of {@link #FT_FACE_FLAG_TRICKY} for more details.
     *
     * @implNote
     * <pre>{@code
     * #define FT_IS_TRICKY( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_TRICKY ) )
     * }</pre>
     */
    public static boolean FTIsTricky(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_TRICKY) != 0;
    }

    /**
     * A macro that returns true whenever a face object is a named instance of a GX or OpenType variation font.<br/>
     * [Since 2.9] Changing the design coordinates with FT_Set_Var_Design_Coordinates or
     * FT_Set_Var_Blend_Coordinates does not influence the return value of this macro
     * (only FT_Set_Named_Instance does that).
     *
     * @since 2.7
     *
     * @implNote
     * <pre>{@code
     * #define FT_IS_NAMED_INSTANCE( face ) \
     *           ( !!( (face)->face_index & 0x7FFF0000L ) )
     * }</pre>
     */
    public static boolean FTIsNamedInstance(MemorySegment face) {
        return (FACE_INDEX.get(face) & 0x7FFF0000) != 0;
    }

    /**
     * A macro that returns true whenever a face object has been altered
     * by FT_Set_MM_Design_Coordinates, FT_Set_Var_Design_Coordinates, or FT_Set_Var_Blend_Coordinates.
     *
     * @implNote
     * <pre>{@code
     * #define FT_IS_VARIATION( face ) \
     *           ( !!( (face)->face_flags & FT_FACE_FLAG_VARIATION ) )
     * }</pre>
     */
    public static boolean FTIsVariation(MemorySegment face) {
        return (FACE_FLAGS.get(face) & FT_FACE_FLAG_VARIATION) != 0;
    }

    static {
        LayoutBuilder builder = new LayoutBuilder("LLLLLAAIAIA01SSSSSSSSAAAAAA20AA", new String[]{
                "num_faces", "face_index", "face_flags", "style_flags", "num_glyphs", "family_name",
                "style_name", "num_fixed_sizes", "available_sizes", "num_charmaps", "charmaps", "generic",
                "bbox", "units_per_EM", "ascender", "descender", "height", "max_advance_width",
                "max_advance_height", "underline_position", "underline_thickness", "glyph", "size",
                "charmap", "driver", "memory", "stream", "sizes_list", "autohint", "extensions", "internal"
        }, FTGeneric.STRUCT_LAYOUT, FTBBox.STRUCT_LAYOUT, FTList.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        NUM_FACES = builder.newLong("num_faces");
        FACE_INDEX = builder.newLong("face_index");
        FACE_FLAGS = builder.newLong("face_flags");
        STYLE_FLAGS = builder.newLong("style_flags");
        NUM_GLYPHS = builder.newLong("num_glyphs");//5Long
        FAMILY_NAME = builder.newAddress("family_name");
        STYLE_NAME = builder.newAddress("style_name");//2Address
        NUM_FIXED_SIZES = builder.newInt("num_fixed_sizes");//1Int
        AVAILABLE_SIZES = builder.newAddress("available_sizes");//1Address
        NUM_CHARMAPS = builder.newInt("num_charmaps");//1Int
        CHARMAPS = builder.newAddress("charmaps");//1Address
        GENERIC = builder.newStruct("generic", FTGeneric.STRUCT_LAYOUT); //10
        BBOX = builder.newStruct("bbox", FTBBox.STRUCT_LAYOUT);//11
        UNITS_PER_EM = builder.newShort("units_per_EM");
        ASCENDER = builder.newShort("ascender");
        DESCENDER = builder.newShort("descender");
        HEIGHT = builder.newShort("height");
        MAX_ADVANCE_WIDTH = builder.newShort("max_advance_width");
        MAX_ADVANCE_HEIGHT = builder.newShort("max_advance_height");
        UNDERLINE_POSITION = builder.newShort("underline_position");
        UNDERLINE_THICKNESS = builder.newShort("underline_thickness"); //8Short
        GLYPH = builder.newAddress("glyph");
        SIZE = builder.newAddress("size");
        CHARMAP = builder.newAddress("charmap");
        DRIVER = builder.newAddress("driver");
        MEMORY = builder.newAddress("memory");
        STREAM = builder.newAddress("stream");//6Address
        SIZES_LIST = builder.newStruct("sizes_list", FTList.STRUCT_LAYOUT);//12
        AUTOHINT = builder.newStruct("autohint", FTGeneric.STRUCT_LAYOUT);//10
        EXTENSIONS = builder.newAddress("extensions");
        INTERNAL = builder.newAddress("internal");//2Address
    }
}
