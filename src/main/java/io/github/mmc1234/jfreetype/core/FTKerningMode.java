package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.CEnum;

/**
 * An enumeration to specify the format of kerning values returned by {@link FreeTypeFace#FTGetKerning}.
 *
 * @apiNote {@link #FT_KERNING_DEFAULT} returns full pixel values; it also makes FreeType heuristically scale
 * down kerning distances at small ppem values so that they don't become too big.<br/>
 * Both {@link #FT_KERNING_DEFAULT} and {@link #FT_KERNING_UNFITTED} use the current horizontal scaling factor
 * (as set e.g. with {@link FreeTypeFace#FTSetCharSize}) to convert font units to pixels.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef enum FT_Kerning_Mode_
 *   {
 *     FT_KERNING_DEFAULT = 0,
 *     FT_KERNING_UNFITTED,
 *     FT_KERNING_UNSCALED
 *
 *   } FT_Kerning_Mode;
 * }</pre>
 */
public enum FTKerningMode implements CEnum {

    /**
     * Return grid-fitted kerning distances in 26.6 fractional pixels.
     */
    FT_KERNING_DEFAULT,

    /**
     * Return un-grid-fitted kerning distances in 26.6 fractional pixels.
     */
    FT_KERNING_UNFITTED,

    /**
     * Return the kerning vector in original font units.
     */
    FT_KERNING_UNSCALED;

    @Override
    public int value() {
        return ordinal();
    }
}
