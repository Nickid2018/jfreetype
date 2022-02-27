package io.github.mmc1234.jfreetype.core;

// TODO

/* Load Glyph Flags */
/* Bits 16-19 are used by `FT_LOAD_TARGET_` */

// --- FT_LOAD_XXX Note:
// By default, hinting is enabled and the font's native hinter (see FT_FACE_FLAG_HINTER) is preferred
// over the auto-hinter. You can disable hinting by setting FT_LOAD_NO_HINTING or change the precedence
// by setting FT_LOAD_FORCE_AUTOHINT. You can also set FT_LOAD_NO_AUTOHINT in case you don't want the auto-hinter
// to be used at all.
// See the description of FT_FACE_FLAG_TRICKY for a special exception (affecting only a handful of Asian fonts).
// Besides deciding which hinter to use, you can also decide which hinting algorithm to use.
// See FT_LOAD_TARGET_XXX for details.
// Note that the auto-hinter needs a valid Unicode cmap (either a native one or synthesized by FreeType)
// for producing correct results. If a font provides an incorrect mapping (for example, assigning
// the character code U+005A, LATIN CAPITAL LETTER Z, to a glyph depicting a mathematical integral sign),
// the auto-hinter might produce useless results.
// --- Note End

public interface FTLoadFlags {
    /**
     * Corresponding to 0, this value is used as the default glyph load operation. In this case, the following happens:<br/>
     * 1. FreeType looks for a bitmap for the glyph corresponding to the face's current size.
     * If one is found, the function returns. The bitmap data can be accessed from the glyph slot (see note below).<br/>
     * 2. If no embedded bitmap is searched for or found, FreeType looks for a scalable outline. If one is found,
     * it is loaded from the font file, scaled to device pixels, then ‘hinted’ to the pixel grid in order to optimize it.
     * The outline data can be accessed from the glyph slot (see note below).<br/>
     * Note that by default the glyph loader doesn't render outlines into bitmaps. The following flags are used
     * to modify this default behaviour to more specific and useful cases.
     */
    int FT_LOAD_DEFAULT = 0x0;
    /**
     * Don't scale the loaded outline glyph but keep it in font units.<br/>
     * This flag implies {@link #FT_LOAD_NO_HINTING} and {@link #FT_LOAD_NO_BITMAP}, and unsets {@link #FT_LOAD_RENDER}.<br/>
     * If the font is ‘tricky’ (see {@code FT_FACE_FLAG_TRICKY} for more), using {@link #FT_LOAD_NO_SCALE} usually
     * yields meaningless outlines because the subglyphs must be scaled and positioned with hinting instructions.
     * This can be solved by loading the font without {@link #FT_LOAD_NO_SCALE} and setting the character size to font->units_per_EM.
     */
    int FT_LOAD_NO_SCALE = 1;
    /**
     * Disable hinting. This generally generates ‘blurrier’ bitmap glyphs when the glyph are rendered in any of
     * the anti-aliased modes. See also the note below.<br/>
     * This flag is implied by {@link #FT_LOAD_NO_SCALE}.
     */
    int FT_LOAD_NO_HINTING = 1 << 1;
    /**
     * Call {@link FreeTypeGlyph#FTRenderGlyph} after the glyph is loaded. By default, the glyph is rendered in {@code FT_RENDER_MODE_NORMAL} mode.
     * This can be overridden by FT_LOAD_TARGET_XXX or {@link #FT_LOAD_MONOCHROME}.<br/>
     * This flag is unset by {@link #FT_LOAD_NO_SCALE}.
     */
    int FT_LOAD_RENDER = 1 << 2;
    /**
     * Ignore bitmap strikes when loading. Bitmap-only fonts ignore this flag.<br/>
     * {@link #FT_LOAD_NO_SCALE} always sets this flag.
     */
    int FT_LOAD_NO_BITMAP = 1 << 3;
    /**
     * Load the glyph for vertical text layout. In particular, {@link FTGlyphSlot#ADVANCE} is set to
     * {@link FTGlyphMetrics#VERT_ADVANCE}.<br/>
     * In case {@code FT_HAS_VERTICAL} doesn't return true, you shouldn't use this flag currently.
     * Reason is that in this case vertical metrics get synthesized, and those values are not always
     * consistent across various font formats.
     */
    int FT_LOAD_VERTICAL_LAYOUT = 1 << 4;
    /**
     * Prefer the auto-hinter over the font's native hinter. See also the note below.
     */
    int FT_LOAD_FORCE_AUTOHINT = 1 << 5;
    /**
     * Ignored. Deprecated.
     */
    int FT_LOAD_CROP_BITMAP = 1 << 6;
    /**
     * Make the font driver perform pedantic verifications during glyph loading and hinting.
     * This is mostly used to detect broken glyphs in fonts. By default, FreeType tries to handle broken fonts also.<br/>
     * In particular, errors from the TrueType bytecode engine are not passed to the application if this flag is not set;
     * this might result in partially hinted or distorted glyphs in case a glyph's bytecode is buggy.
     */
    int FT_LOAD_PEDANTIC = 1 << 7;
    /**
     * Ignored. Deprecated.
     */
    int FT_LOAD_IGNORE_GLOBAL_ADVANCE_WIDTH = 1 << 9;
    /**
     * Don't load composite glyphs recursively. Instead, the font driver fills {@link FTGlyphSlot#NUM_SUBGLYPHS} and
     * {@link FTGlyphSlot#SUBGLYPHS}; it also sets {@link FTGlyphSlot#FORMAT} to {@code FT_GLYPH_FORMAT_COMPOSITE}.
     * The description of subglyphs can then be accessed with {@link FreeTypeGlyph#FTGetSubGlyphInfo}.<br/>
     * Don't use this flag for retrieving metrics information since some font drivers only return rudimentary data.<br/>
     * This flag implies {@link #FT_LOAD_NO_SCALE} and {@link #FT_LOAD_IGNORE_TRANSFORM}.
     */
    int FT_LOAD_NO_RECURSE = 1 << 10;
    /**
     * Ignore the transform matrix set by {@link FreeTypeFace#FTSetTransform}.
     */
    int FT_LOAD_IGNORE_TRANSFORM = 1 << 11;
    /**
     * This flag is used with {@link #FT_LOAD_RENDER} to indicate that you want to render an outline glyph
     * to a 1-bit monochrome bitmap glyph, with 8 pixels packed into each byte of the bitmap data.<br/>
     * Note that this has no effect on the hinting algorithm used. You should rather use {@code FT_LOAD_TARGET_MONO}
     * so that the monochrome-optimized hinting algorithm is used.
     */
    int FT_LOAD_MONOCHROME = 1 << 12;
    /**
     * Keep {@link FTGlyphSlot#LINEAR_HORI_ADVANCE} and {@link FTGlyphSlot#LINEAR_VERT_ADVANCE} in font units.
     * See {@link FTGlyphSlot} for details.
     */
    int FT_LOAD_LINEAR_DESIGN = 1 << 13;
    /**
     * Disable the auto-hinter. See also the note below.
     */
    int FT_LOAD_NO_AUTOHINT = 1 << 15;
    /**
     * Load colored glyphs. There are slight differences depending on the font format.<br/>
     * [Since 2.5] Load embedded color bitmap images. The resulting color bitmaps, if available,
     * will have the {@link io.github.mmc1234.jfreetype.image.FTPixelMode#FT_PIXEL_MODE_BGRA} format,
     * with pre-multiplied color channels. If the flag is not set
     * and color bitmaps are found, they are converted to 256-level gray bitmaps,
     * using the {@link io.github.mmc1234.jfreetype.image.FTPixelMode#FT_PIXEL_MODE_GRAY} format.<br/>
     * [Since 2.10, experimental] If the glyph index contains an entry in the face's ‘COLR’ table with
     * a ‘CPAL’ palette table (as defined in the OpenType specification), make {@link FreeTypeGlyph#FTRenderGlyph} provide
     * a default blending of the color glyph layers associated with the glyph index, using the same bitmap format
     * as embedded color bitmap images. This is mainly for convenience; for full control of color layers use
     * {@code FT_Get_Color_Glyph_Layer} and FreeType's color functions like FT_Palette_Select instead of
     * setting {@link #FT_LOAD_COLOR} for rendering so that the client application can handle blending by itself.
     */
    int FT_LOAD_COLOR = 1 << 20;
    /**
     * [Since 2.6.1] Compute glyph metrics from the glyph data, without the use of bundled metrics tables
     * (for example, the ‘hdmx’ table in TrueType fonts). This flag is mainly used by font validating or
     * font editing applications, which need to ignore, verify, or edit those tables.<br/>
     * Currently, this flag is only implemented for TrueType fonts.
     */
    int FT_LOAD_COMPUTE_METRICS = 1 << 21;
    /**
     * [Since 2.7.1] Request loading of the metrics and bitmap image information of a (possibly embedded)
     * bitmap glyph without allocating or copying the bitmap image data itself. No effect if the target glyph
     * is not a bitmap image.<br/>
     * This flag unsets {@link #FT_LOAD_RENDER}.
     */
    int FT_LOAD_BITMAP_METRICS_ONLY = 1 << 22;
}
