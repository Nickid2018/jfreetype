package io.github.mmc1234.jfreetype.glyph;

import io.github.mmc1234.jfreetype.CEnum;

/**
 * The mode how the values of {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGlyphGetCBox} are returned.
 *
 * @implNote In freetype/ftglyph.h
 * <pre>{@code
 *   typedef enum  FT_Glyph_BBox_Mode_
 *   {
 *     FT_GLYPH_BBOX_UNSCALED  = 0,
 *     FT_GLYPH_BBOX_SUBPIXELS = 0,
 *     FT_GLYPH_BBOX_GRIDFIT   = 1,
 *     FT_GLYPH_BBOX_TRUNCATE  = 2,
 *     FT_GLYPH_BBOX_PIXELS    = 3
 *   } FT_Glyph_BBox_Mode;
 * }</pre>
 */
public enum FTGlyphBBoxMode implements CEnum {

    /**
     * Return unscaled font units.
     */
    FT_GLYPH_BBOX_UNSCALED(0),

    /**
     * Return unfitted 26.6 coordinates.
     */
    FT_GLYPH_BBOX_SUBPIXELS(0),

    /**
     * Return grid-fitted 26.6 coordinates.
     */
    FT_GLYPH_BBOX_GRIDFIT(1),

    /**
     * Return coordinates in integer pixels.
     */
    FT_GLYPH_BBOX_TRUNCATE(2),

    /**
     * Return grid-fitted pixel coordinates.
     */
    FT_GLYPH_BBOX_PIXELS(3);

    private final int value;

    FTGlyphBBoxMode(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
