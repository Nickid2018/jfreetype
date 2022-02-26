package io.github.mmc1234.jfreetype.image;

import io.github.mmc1234.jfreetype.CEnum;

/**
 * An enumeration type used to describe the format of a given glyph image.
 * Note that this version of FreeType only supports two image formats,
 * even though future font drivers will be able to register their own format.
 *
 * @implNote In freetype/ftimage.h
 * <pre>{@code
 *   #define FT_IMAGE_TAG( value, _x1, _x2, _x3, _x4 )                       \
 *           value = ( ( FT_STATIC_BYTE_CAST( unsigned long, _x1 ) << 24 ) | \
 *                     ( FT_STATIC_BYTE_CAST( unsigned long, _x2 ) << 16 ) | \
 *                     ( FT_STATIC_BYTE_CAST( unsigned long, _x3 ) << 8  ) | \
 *                       FT_STATIC_BYTE_CAST( unsigned long, _x4 )         )
 *   typedef enum FT_Glyph_Format_
 *   {
 *     FT_IMAGE_TAG( FT_GLYPH_FORMAT_NONE, 0, 0, 0, 0 ),
 *
 *     FT_IMAGE_TAG( FT_GLYPH_FORMAT_COMPOSITE, 'c', 'o', 'm', 'p' ),
 *     FT_IMAGE_TAG( FT_GLYPH_FORMAT_BITMAP,    'b', 'i', 't', 's' ),
 *     FT_IMAGE_TAG( FT_GLYPH_FORMAT_OUTLINE,   'o', 'u', 't', 'l' ),
 *     FT_IMAGE_TAG( FT_GLYPH_FORMAT_PLOTTER,   'p', 'l', 'o', 't' )
 *   } FT_Glyph_Format;
 * }</pre>
 */
public enum FTGlyphFormat implements CEnum<FTGlyphFormat> {

    /**
     * The value 0 is reserved.
     */
    FT_GLYPH_FORMAT_NONE(0, 0, 0, 0),

    /**
     * The glyph image is a composite of several other images.
     * This format is only used with {@link io.github.mmc1234.jfreetype.core.FreeType#FT_LOAD_NO_RECURSE},
     * and is used to report compound glyphs (like accented characters).
     */
    FT_GLYPH_FORMAT_COMPOSITE('c', 'o', 'm', 'p' ),

    /**
     * The glyph image is a bitmap, and can be described as an {@link FTBitmap}.
     * You generally need to access {@link io.github.mmc1234.jfreetype.core.FTGlyphSlot#BITMAP} to read it.
     */
    FT_GLYPH_FORMAT_BITMAP('b', 'i', 't', 's' ),

    /**
     * The glyph image is a vectorial outline made of line segments and Bezier arcs;
     * it can be described as an {@link FTOutline}; you generally want to access
     * {@link io.github.mmc1234.jfreetype.core.FTGlyphSlot#OUTLINE} to read it.
     */
    FT_GLYPH_FORMAT_OUTLINE('o', 'u', 't', 'l' ),

    /**
     * The glyph image is a vectorial path with no inside and outside contours.
     * Some Type 1 fonts, like those in the Hershey family, contain glyphs in this format.
     * These are described as {@link FTOutline}, but FreeType isn't currently capable of rendering them correctly.
     */
    FT_GLYPH_FORMAT_PLOTTER('p', 'l', 'o', 't' );

    private final int value;

    FTGlyphFormat(int a, int b, int c, int d) {
        value = (a << 24) | (b << 16) | (c << 8) | d;
    }

    @Override
    public int value() {
        return value;
    }
}
