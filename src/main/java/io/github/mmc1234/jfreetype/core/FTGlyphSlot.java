package io.github.mmc1234.jfreetype.core;

/**
 * A handle to a given ‘glyph slot’
 * A slot is a container that can hold any of the glyphs contained in its parent face.<br/>
 * the slot's content is erased by the new glyph data, i.e., the glyph's metrics
 * , its image (bitmap or outline), and other control information.
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
public interface FTGlyphSlot {


}
