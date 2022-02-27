package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.image.FTBitmap;
import io.github.mmc1234.jfreetype.image.FTOutline;
import io.github.mmc1234.jfreetype.image.FTVector;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

/**
 * A handle to a given ‘glyph slot’
 * A slot is a container that can hold any of the glyphs contained in its parent face.<br/>
 * the slot's content is erased by the new glyph data, i.e., the glyph's metrics
 * , its image (bitmap or outline), and other control information.
 *
 * @apiNote If {@link FreeTypeGlyph#FTLoadGlyph} is called with default flags (see {@link FTLoadFlags#FT_LOAD_DEFAULT})
 * the glyph image is loaded in the glyph slot in its native format (e.g., an outline glyph for TrueType and Type 1 formats).
 * [Since 2.9] The prospective bitmap metrics are calculated according to FT_LOAD_TARGET_XXX and other flags
 * even for the outline glyph, even if {@link FTLoadFlags#FT_LOAD_RENDER} is not set.<br/>
 * This image can later be converted into a bitmap by calling {@link FreeTypeGlyph#FTRenderGlyph}.
 * This function searches the current renderer for the native image's format, then invokes it.<br/>
 * The renderer is in charge of transforming the native image through the slot's face transformation fields,
 * then converting it into a bitmap that is returned in slot->bitmap.<br/>
 * Note that slot->bitmap_left and slot->bitmap_top are also used to specify the position of the bitmap
 * relative to the current pen position (e.g., coordinates (0,0) on the baseline). Of course,
 * slot->format is also changed to {@link io.github.mmc1234.jfreetype.image.FTGlyphFormat#FT_GLYPH_FORMAT_BITMAP}.<br/>
 * Here is a small pseudocode fragment that shows how to use lsb_delta and rsb_delta to do fractional positioning of glyphs:
 * <pre>{@code
 *   MemorySegment slot     = VarUtils.getSegment(FTFace.GLYPH, face, FTGlyphSlot.STRUCT_LAYOUT, scope);
 *   long          origin_x = 0;
 *
 *   for all glyphs do
 *     [load glyph with `FTLoadGlyph']
 *
 *     FTOutlineTranslate(VarUtils.getAddress(FTGlyphSlot.OUTLINE, slot), origin_x & 63, 0);
 *
 *     [save glyph image, or render glyph, or ...]
 *
 *     [compute kern between current and next glyph
 *      and add it to `origin_x']
 *
 *     origin_x += VarUtils.getLong(VarUtils.createAccess(FTGlyphSlot.SEQUENCE_LAYOUT, "advance.x"), slot);
 *     origin_x += VarUtils.getLong(FTGlyphSlot.LSB_DELTA, slot) - VarUtils.getLong(FTGlyphSlot.RSB_DELTA, slot);
 *   end for
 * }</pre>
 * Here is another small pseudocode fragment that shows how to use lsb_delta and rsb_delta to improve
 * integer positioning of glyphs:
 * <pre>{@code
 *   MemorySegment slot           = VarUtils.getSegment(FTFace.GLYPH, face, FTGlyphSlot.STRUCT_LAYOUT, scope);
 *   long          origin_x       = 0;
 *   long          prev_rsb_delta = 0;
 *
 *   for all glyphs do
 *     [compute kern between current and previous glyph
 *      and add it to `origin_x']
 *
 *     [load glyph with `FTLoadGlyph']
 *
 *     if (prev_rsb_delta - VarUtils.getLong(FTGlyphSlot.LSB_DELTA, slot) > 32)
 *       origin_x -= 64;
 *     else if (prev_rsb_delta - VarUtils.getLong(FTGlyphSlot.LSB_DELTA, slot) < -31 )
 *       origin_x += 64;
 *
 *     prev_rsb_delta = VarUtils.getLong(FTGlyphSlot.RSB_DELTA, slot);
 *
 *     [save glyph image, or render glyph, or ...]
 *
 *     origin_x += VarUtils.getLong(VarUtils.createAccess(FTGlyphSlot.SEQUENCE_LAYOUT, "advance.x"), slot);
 *   end for
 * }</pre>
 * If you use strong auto-hinting, you must apply these delta values! Otherwise, you will experience
 * far too large inter-glyph spacing at small rendering sizes in most cases. Note that it doesn't harm
 * to use the above code for other hinting modes also, since the delta values are zero then.
 *
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct FT_GlyphSlotRec_
 *   {
 *     FT_Library        library;
 *     FT_Face           face;
 *     FT_GlyphSlot      next;
 *     FT_UInt           glyph_index; // new in 2.10; was reserved previously
 *     FT_Generic        generic;
 *
 *     FT_Glyph_Metrics  metrics;
 *     FT_Fixed          linearHoriAdvance;
 *     FT_Fixed          linearVertAdvance;
 *     FT_Vector         advance;
 *
 *     FT_Glyph_Format   format;
 *
 *     FT_Bitmap         bitmap;
 *     FT_Int            bitmap_left;
 *     FT_Int            bitmap_top;
 *
 *     FT_Outline        outline;
 *
 *     FT_UInt           num_subglyphs;
 *     FT_SubGlyph       subglyphs;
 *
 *     void*             control_data;
 *     long              control_len;
 *
 *     FT_Pos            lsb_delta;
 *     FT_Pos            rsb_delta;
 *
 *     void*             other;
 *
 *     FT_Slot_Internal  internal;
 *
 *   } FT_GlyphSlotRec;
 *   typedef struct FT_GlyphSlotRec_* FT_GlyphSlot;
 * }</pre>
 */
public final class FTGlyphSlot {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * A handle to the FreeType library instance this slot belongs to.
     */
    public static final VarHandle LIBRARY;

    /**
     * A handle to the parent face object.
     */
    public static final VarHandle FACE;

    /**
     * In some cases (like some font tools), several glyph slots per face object can be a good thing.
     * As this is rare, the glyph slots are listed through a direct, single-linked list using its {@link #NEXT}.
     */
    public static final VarHandle NEXT;

    /**
     * [Since 2.10] The glyph index passed as an argument to {@link FreeTypeGlyph#FTLoadGlyph} while initializing the glyph slot.
     */
    public static final VarHandle GLYPH_INDEX;

    /**
     * A typeless pointer unused by the FreeType library or any of its drivers.
     * It can be used by client applications to link their own data to each glyph slot object.
     */
    public static final MethodHandle GENERIC;

    /**
     * The metrics of the last loaded glyph in the slot. The returned values depend on the last load flags
     * (see the {@link FreeTypeGlyph#FTLoadGlyph} API function) and can be expressed either
     * in 26.6 fractional pixels or font units.<br/>
     * Note that even when the glyph image is transformed, the metrics are not.
     */
    public static final MethodHandle METRICS;

    /**
     * The advance width of the unhinted glyph. Its value is expressed in 16.16 fractional pixels,
     * unless {@link FTLoadFlags#FT_LOAD_LINEAR_DESIGN} is set when loading the glyph. This field can be important
     * to perform correct WYSIWYG layout. Only relevant for outline glyphs.
     */
    public static final VarHandle LINEAR_HORI_ADVANCE;

    /**
     * The advance height of the unhinted glyph. Its value is expressed in 16.16 fractional pixels,
     * unless {@link FTLoadFlags#FT_LOAD_LINEAR_DESIGN} is set when loading the glyph. This field can be important
     * to perform correct WYSIWYG layout. Only relevant for outline glyphs.
     */
    public static final VarHandle LINEAR_VERT_ADVANCE;

    /**
     * This shorthand is, depending on {@link FTLoadFlags#FT_LOAD_IGNORE_TRANSFORM}, the transformed (hinted) advance width
     * for the glyph, in 26.6 fractional pixel format. As specified with {@link FTLoadFlags#FT_LOAD_VERTICAL_LAYOUT},
     * it uses either the horiAdvance or the vertAdvance value of metrics field.
     */
    public static final MethodHandle ADVANCE;

    /**
     * This field indicates the format of the image contained in the glyph slot.
     * Typically {@link io.github.mmc1234.jfreetype.image.FTGlyphFormat#FT_GLYPH_FORMAT_BITMAP},
     * {@link io.github.mmc1234.jfreetype.image.FTGlyphFormat#FT_GLYPH_FORMAT_OUTLINE},
     * or {@link io.github.mmc1234.jfreetype.image.FTGlyphFormat#FT_GLYPH_FORMAT_COMPOSITE}, but other values are possible.
     */
    public static final VarHandle FORMAT;

    /**
     * This field is used as a bitmap descriptor. Note that the newAddress and content of the bitmap buffer
     * can change between calls of {@link FreeTypeGlyph#FTLoadGlyph} and a few other functions.
     */
    public static final MethodHandle BITMAP;

    /**
     * The bitmap's left bearing expressed in integer pixels.
     */
    public static final VarHandle BITMAP_LEFT;

    /**
     * The bitmap's top bearing expressed in integer pixels. This is the distance from the baseline to
     * the top-most glyph scanline, upwards y coordinates being <b>positive</b>.
     */
    public static final VarHandle BITMAP_TOP;

    /**
     * The outline descriptor for the current glyph image if its format is
     * {@link io.github.mmc1234.jfreetype.image.FTGlyphFormat#FT_GLYPH_FORMAT_OUTLINE}.
     * Once a glyph is loaded, outline can be transformed, distorted, emboldened, etc.
     * However, it must not be freed.<br/>
     * [Since 2.10.1] If {@link FTLoadFlags#FT_LOAD_NO_SCALE} is set, outline coordinates of OpenType variation fonts
     * for a selected instance are internally handled as 26.6 fractional font units but returned as (rounded) integers,
     * as expected. To get unrounded font units, don't use FT_LOAD_NO_SCALE but load the glyph
     * with {@link FTLoadFlags#FT_LOAD_NO_HINTING} and scale it, using the font's units_per_EM value as the ppem.
     */
    public static final MethodHandle OUTLINE;

    /**
     * The number of subglyphs in a composite glyph.
     * This field is only valid for the composite glyph format that should normally only be loaded
     * with the {@link FTLoadFlags#FT_LOAD_NO_RECURSE} flag.
     */
    public static final VarHandle NUM_SUBGLYPHS;

    /**
     * An array of subglyph descriptors for composite glyphs.
     * There are {@link #NUM_SUBGLYPHS} elements in there. Currently, internal to FreeType.
     */
    public static final VarHandle SUBGLYPHS;

    /**
     * Certain font drivers can also return the control data for a given glyph image (e.g. TrueType bytecode, Type 1 charstrings, etc.).
     * This field is a pointer to such data; it is currently internal to FreeType.
     */
    public static final VarHandle CONTROL_DATA;

    /**
     * This is the length in bytes of the control data. Currently, internal to FreeType.
     */
    public static final VarHandle CONTROL_LEN;

    /**
     * The difference between hinted and unhinted left side bearing while auto-hinting is active. Zero otherwise.
     */
    public static final VarHandle LSB_DELTA;

    /**
     * The difference between hinted and unhinted right side bearing while auto-hinting is active. Zero otherwise.
     */
    public static final VarHandle RSB_DELTA;

    /**
     * Reserved.
     */
    public static final VarHandle OTHER;
    public static final VarHandle INTERNAL;

    static {
        LayoutBuilder builder = new LayoutBuilder("AAAI01LL2I3II4IAALLLAA", new String[] {
                "library", "face", "next", "glyph_index", "generic", "metrics", "linearHoriAdvance",
                "linearVertAdvance", "advance", "format", "bitmap", "bitmap_left", "bitmap_top",
                "outline", "num_subglyphs", "subglyphs", "control_data", "control_len", "lsb_delta",
                "rsb_delta", "other", "internal"
        }, FTGeneric.STRUCT_LAYOUT, FTGlyphMetrics.STRUCT_LAYOUT, FTVector.STRUCT_LAYOUT,
                FTBitmap.STRUCT_LAYOUT, FTOutline.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        LIBRARY = builder.primitiveField("library");
        FACE = builder.primitiveField("face");
        NEXT = builder.primitiveField("next");
        GLYPH_INDEX = builder.primitiveField("glyph_index");
        LINEAR_HORI_ADVANCE = builder.primitiveField("linearHoriAdvance");
        LINEAR_VERT_ADVANCE = builder.primitiveField("linearVertAdvance");
        FORMAT = builder.primitiveField("format");
        BITMAP_LEFT = builder.primitiveField("bitmap_left");
        BITMAP_TOP = builder.primitiveField("bitmap_top");
        NUM_SUBGLYPHS = builder.primitiveField("num_subglyphs");
        SUBGLYPHS = builder.primitiveField("subglyphs");
        CONTROL_DATA = builder.primitiveField("control_data");
        CONTROL_LEN = builder.primitiveField("control_len");
        LSB_DELTA = builder.primitiveField("lsb_delta");
        RSB_DELTA = builder.primitiveField("rsb_delta");
        OTHER = builder.primitiveField("other");
        INTERNAL = builder.primitiveField("internal");

        GENERIC = builder.structField("generic");
        METRICS = builder.structField("metrics");
        ADVANCE = builder.structField("advance");
        BITMAP = builder.structField("bitmap");
        OUTLINE = builder.structField("outline");
    }
}
