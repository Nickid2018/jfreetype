package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.internal.BaseInterface;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

public interface FreeTypeGlyph {
    /**
     * Convert a given glyph image to a bitmap. It does so by inspecting the glyph image format,
     * finding the relevant renderer, and invoking it.
     *
     * @param slot        A handle to the glyph slot containing the image to convert.
     * @param render_mode The render mode used to render the glyph image into a bitmap. See FT_Render_Mode for a list of possible values.
     *                    <br/>
     *                    If FT_RENDER_MODE_NORMAL is used, a previous call of FT_Load_Glyph with flag FT_LOAD_COLOR makes FT_Render_Glyph provide
     *                    a default blending of colored glyph layers associated with
     *                    the current glyph slot (provided the font contains such layers) instead of rendering
     *                    the glyph slot's outline.
     *                    This is an experimental feature; see {@code FT_LOAD_COLOR} for more information.
     * @return The render mode used to render the glyph image into a bitmap. See {@link FTRenderMode} for a list of possible values.<br/>
     * If {@code FT_RENDER_MODE_NORMAL} is used, a previous call of {@link FreeTypeFace#FTLoadGlyph} with flag
     * {@code FT_LOAD_COLOR} makes {@link #FTRenderGlyph} provide a default blending of colored glyph layers associated with
     * the current glyph slot (provided the font contains such layers) instead of rendering the glyph slot's outline.
     * This is an experimental feature; see FT_LOAD_COLOR for more information.
     * @apiNote To get meaningful results, font scaling values must be set with functions like {@link FreeTypeFace#FTSetCharSize}
     * before calling {@link #FTRenderGlyph}.<br/>
     * When FreeType outputs a bitmap of a glyph, it really outputs an alpha coverage map. If a pixel is
     * completely covered by a filled-in outline, the bitmap contains 0xFF at that pixel, meaning that 0xFF/0xFF
     * fraction of that pixel is covered, meaning the pixel is 100% black (or 0% bright). If a pixel is only 50%
     * covered (value 0x80), the pixel is made 50% black (50% bright or a middle shade of grey). 0% covered means
     * 0% black (100% bright or white).<br/>
     * On high-DPI screens like on smartphones and tablets, the pixels are so small that their chance of being
     * completely covered and therefore completely black are fairly good. On the low-DPI screens, however, the
     * situation is different. The pixels are too large for most of the details of a glyph and shades of gray
     * are the norm rather than the exception.<br/>
     * This is relevant because all our screens have a second problem: they are not linear. 1 + 1 is not 2.
     * Twice the value does not result in twice the brightness. When a pixel is only 50% covered, the coverage
     * map says 50% black, and this translates to a pixel value of 128 when you use 8 bits per channel (0-255).
     * However, this does not translate to 50% brightness for that pixel on our sRGB and gamma 2.2 screens.
     * Due to their non-linearity, they dwell longer in the darks and only a pixel value of about 186 results in
     * 50% brightness – 128 ends up too dark on both bright and dark backgrounds. The net result is that dark text
     * looks burnt-out, pixely and blotchy on bright background, bright text too frail on dark backgrounds,
     * and colored text on colored background (for example, red on green) seems to have dark halos or ‘dirt’
     * around it. The situation is especially ugly for diagonal stems like in ‘w’ glyph shapes where the quality
     * of FreeType's anti-aliasing depends on the correct display of grays. On high-DPI screens where smaller,
     * fully black pixels reign supreme, this doesn't matter, but on our low-DPI screens with all the gray shades,
     * it does. 0% and 100% brightness are the same things in linear and non-linear space, just all the shades
     * in-between aren't.<br/>
     * The blending function for placing text over a background is
     *
     * <pre>{@code
     *          dst = alpha * src + (1 - alpha) * dst
     *          }</pre>
     * <p>
     * , which is known as the OVER operator.<br/>
     * To correctly composite an anti-aliased pixel of a glyph onto a surface,
     * <ul>
     *     <li>
     *         take the foreground and background colors (e.g., in sRGB space) and apply gamma to get them in a
     *         linear space,
     *     </li>
     *     <li>
     *         use OVER to blend the two linear colors using the glyph pixel as the alpha value (remember, the
     *         glyph bitmap is an alpha coverage bitmap), and
     *     </li>
     *     <li> apply inverse gamma to the blended pixel and write it back to the image.</li>
     * </ul>
     * Internal testing at Adobe found that a target inverse gamma of 1.8 for step 3 gives good results across a
     * wide range of displays with an sRGB gamma curve or a similar one.<br/>
     * This process can cost performance. There is an approximation that does not need to know about the background
     * color; see <a href="https://bel.fi/alankila/lcd/">https://bel.fi/alankila/lcd/</a> and
     * <a href="https://bel.fi/alankila/lcd/alpcor.html">https://bel.fi/alankila/lcd/alpcor.html</a>
     * for details.<br/>
     * ATTENTION: Linear blending is even more important when dealing with subpixel-rendered glyphs to prevent
     * color-fringing! A subpixel-rendered glyph must first be filtered with a filter that gives equal
     * weight to the three color primaries and does not exceed a sum of 0x100, see section ‘Subpixel
     * Rendering’. Then the only difference to gray linear blending is that subpixel-rendered linear blending is done
     * 3 times per pixel: red foreground subpixel to red background subpixel and so on for green and blue.
     */
    static int FTRenderGlyph(@In MemoryAddress slot, @In FTRenderMode render_mode) {
        try {
            return (int) BaseInterface.FT_RENDER_GLYPH.invoke(slot, render_mode.ordinal());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Retrieve a description of a given subglyph. Only use it if glyph->format is {@code FT_GLYPH_FORMAT_COMPOSITE};
     * an error is returned otherwise.
     *
     * @param glyph       Retrieve a description of a given subglyph. Only use it if glyph->format is FT_GLYPH_FORMAT_COMPOSITE; an error is returned otherwise.
     * @param sub_index   The index of the subglyph. Must be less than glyph->num_subglyphs.
     * @param p_index     The glyph index of the subglyph.
     * @param p_flags     The subglyph flags, see FT_SUBGLYPH_FLAG_XXX.
     * @param p_arg1      The subglyph's first argument (if any).
     * @param p_arg2      The subglyph's second argument (if any).
     * @param p_transform The subglyph's second argument (if any).
     * @return The subglyph's second argument (if any).
     * @apiNote The values of *p_arg1, *p_arg2, and *p_transform must be interpreted depending on the flags returned in
     * *p_flags. See the <a href="https://docs.microsoft.com/en-us/typography/opentype/spec/glyf#composite-glyph-description">
     * OpenType specification</a> for details.
     */
    static int FTGetSubGlyphInfo(@In MemoryAddress glyph, @In int sub_index, @Out MemorySegment p_index, @Out MemorySegment p_flags, @Out MemorySegment p_arg1, @Out MemorySegment p_arg2, @Out MemorySegment p_transform) {
        try {
            return (int) BaseInterface.FT_GET_SUBGLYPH_INFO.invoke(glyph, sub_index, p_index.address(), p_flags.address(), p_arg1.address(), p_arg2.address(), p_transform.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }
}
