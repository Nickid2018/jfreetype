package com.github.mmc1234.jfreetype;

/**
 * A handle to an object that models a face scaled to a given character size.
 * <p>
 * Note:
 * <p>
 * An {@link FTFace} has one active {@link FTSize} object that is used
 * by functions like {@link FreeType#FTLoadGlyph} to determine
 * the scaling transformation that in turn is used to load and hint glyphs and metrics.
 * <p>
 * You can use {@link FreeType#FTSetCharSize}, {@link FreeType#FTSetPixelSizes},
 * {@link FreeType#FTRequestSize} or even {@link FreeType#FTSelectSize} to change
 * the content (i.e., the scaling values) of the active {@link FTSize}.
 * <p>
 * You can use FTNewSize to create additional size objects for a given {@link FTFace},
 * but they won't be used by other functions until you activate it through FTActivateSize.
 * Only one size can be activated at any given time per face.
 */
public interface FTSize {

}
