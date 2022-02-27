package io.github.mmc1234.jfreetype.core;

/**
 * The subglyph structure is an internal object used to describe subglyphs (for example, in the case of composites).
 *
 * @apiNote The subglyph implementation is not part of the high-level API, hence the forward structure declaration.<br/>
 * You can however retrieve subglyph information with {@link FreeTypeGlyph#FTGetSubGlyphInfo}.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct FT_SubGlyphRec_*  FT_SubGlyph;
 * }</pre>
 */
public final class FTSubGlyph {

    // Hidden struct
}
