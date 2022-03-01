package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.Struct;
import io.github.mmc1234.jfreetype.color.*;
import io.github.mmc1234.jfreetype.glyph.FTGlyph;
import io.github.mmc1234.jfreetype.image.FTVector;
import io.github.mmc1234.jfreetype.internal.BaseInterface;
import io.github.mmc1234.jfreetype.internal.GlyphColorManagement;
import io.github.mmc1234.jfreetype.internal.GlyphLayerManagement;
import io.github.mmc1234.jfreetype.internal.GlyphManagement;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

/**
 * An interface stores glyph operations.
 */
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
     * If {@code FT_RENDER_MODE_NORMAL} is used, a previous call of {@link FreeTypeGlyph#FTLoadGlyph} with flag
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

    // --- Glyph Color Management

    /**
     * Retrieve the face's color palette data.
     *
     * @apiNote All arrays in the returned {@link FTPaletteData} structure are read-only.
     * This function always returns an error if the config macro {@code TT_CONFIG_OPTION_COLOR_LAYERS} is not defined in ftoption.h.
     *
     * @param face The source face handle.
     * @param apalette A pointer to an {@link FTPaletteData} structure.
     * @return FreeType error code. 0 means success.
     */
    static int FTPaletteDataGet(@In MemoryAddress face, @Out MemorySegment apalette) {
        try {
            return (int) GlyphColorManagement.FT_PALETTE_DATA_GET.invoke(face, apalette.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * This function has two purposes.<br/>
     * (1) It activates a palette for rendering color glyphs, and<br/>
     * (2) it retrieves all (unmodified) color entries of this palette. This function returns a read-write array,
     * which means that a calling application can modify the palette entries on demand.<br/>
     * A corollary of (2) is that calling the function, then modifying some values, then calling
     * the function again with the same arguments resets all color entries to the original ‘CPAL’ values;
     * all user modifications are lost.
     *
     * @apiNote The array pointed to by apalette_entries is owned and managed by FreeType.<br/>
     * This function always returns an error if the config macro {@code TT_CONFIG_OPTION_COLOR_LAYERS} is not defined in ftoption.h.
     *
     * @param face The source face handle.
     * @param palette_index The palette index.
     * @param apalette An array of color entries for a palette with index palette_index,
     *                 having num_palette_entries elements (as found in the {@link FTPaletteData} structure).
     *                 If apalette is set to NULL, no array gets returned (and no color entries can be modified).<br/>
     *                 In case the font doesn't support color palettes, NULL is returned.
     * @return FreeType error code. 0 means success.
     */
    static int FTPaletteSelect(@In MemoryAddress face, short palette_index, @Out MemorySegment apalette) {
        try {
            return (int) GlyphColorManagement.FT_PALETTE_SELECT.invoke(face, palette_index, apalette.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }


    /**
     * ‘COLR’ uses palette index 0xFFFF to indicate a ‘text foreground color’. This function sets this value.
     *
     * @apiNote If this function isn't called, the text foreground color is set to white opaque
     * (BGRA value 0xFFFFFFFF) if {@link FTPaletteData#FT_PALETTE_FOR_DARK_BACKGROUND} is present for the current palette,
     * and black opaque (BGRA value 0x000000FF) otherwise, including the case that no palette types
     * are available in the ‘CPAL’ table.<br/>
     * This function always returns an error if the config macro {@code TT_CONFIG_OPTION_COLOR_LAYERS}
     * is not defined in ftoption.h.
     *
     * @param face
     * @param foregroundColor
     * @return
     */
    static int FTPaletteSetForegroundColor(@In MemoryAddress face, @In @Struct MemorySegment foregroundColor) {
        try {
            return (int) GlyphColorManagement.FT_PALETTE_SELECT.invoke(face, foregroundColor.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * This is an interface to the ‘COLR’ table in OpenType fonts to iteratively retrieve the colored glyph layers
     * associated with the current glyph slot.<br/>
     * https://docs.microsoft.com/en-us/typography/opentype/spec/colr<br/>
     * The glyph layer data for a given glyph index, if present, provides an alternative, multi-color glyph representation:
     * Instead of rendering the outline or bitmap with the given glyph index, glyphs with the indices and colors returned
     * by this function are rendered layer by layer.<br/>
     * The returned elements are ordered in the z direction from bottom to top; the 'n'th element should be rendered
     * with the associated palette color and blended on top of the already rendered layers (elements 0, 1, …, n-1).
     *
     * @apiNote This function is necessary if you want to handle glyph layers by yourself. In particular, functions
     * that operate with {@link FTGlyph} objects (like FT_Get_Glyph or FT_Glyph_To_Bitmap) don't have access to this information.<br/>
     * Note that {@link #FTRenderGlyph} is able to handle colored glyph layers automatically
     * if the {@link FTLoadFlags#FT_LOAD_COLOR} flag is passed to a previous call
     * to {@link FreeTypeGlyph#FTLoadGlyph}. [This is an experimental feature.]
     *
     * @param face A handle to the parent face object.
     * @param base_glyph The glyph index the colored glyph layers are associated with.
     * @param aglyph_index The glyph index of the current layer.
     * @param acolor_index The color index into the font face's color palette of the current layer.
     *                     The value 0xFFFF is special; it doesn't reference a palette entry but indicates
     *                     that the text foreground color should be used instead (to be set up by the application
     *                     outside of FreeType).<br/>
     *                     The color palette can be retrieved with {@link #FTPaletteSelect}.
     * @param iterator An {@link FTLayerIterator} object. For the first call you should set iterator->p to NULL.
     *                 For all following calls, simply use the same object again.
     * @return Value 1 if everything is OK. If there are no more layers (or if there are no layers at all),
     * value 0 gets returned. In case of an error, value 0 is returned also.
     */
    static int FTGetColorGlyphLayer(@In MemoryAddress face,
                                    @In int base_glyph,
                                    @Out MemorySegment aglyph_index,
                                    @Out MemorySegment acolor_index,
                                    @In @Out MemoryAddress iterator) {
        try {
            return (int) GlyphLayerManagement.FT_GET_COLOR_GLYPH_LAYER
                    .invoke(face, base_glyph, aglyph_index.address(), acolor_index.address(), iterator);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * This is the starting point and interface to color gradient information in a ‘COLR’ v1 table in OpenType fonts
     * to recursively retrieve the paint tables for the directed acyclic graph of a colored glyph, given a glyph ID.<br/>
     * https://github.com/googlefonts/colr-gradients-spec<br/>
     * In a ‘COLR’ v1 font, each color glyph defines a directed acyclic graph of nested paint tables,
     * such as PaintGlyph, PaintSolid, PaintLinearGradient, PaintRadialGradient, and so on.
     * Using this function and specifying a glyph ID, one retrieves the root paint table for this glyph ID.<br/>
     * This function allows control whether an initial root transform is returned to configure scaling, transform,
     * and translation correctly on the client's graphics context. The initial root transform is computed and returned
     * according to the values configured for {@link FTSize} and {@link FreeTypeFace#FTSetTransform} on the {@link FTFace} object,
     * see below for details of the root_transform parameter. This has implications for a client ‘COLR’ v1 implementation:
     * When this function returns an initially computed root transform, at the time of executing the
     * FTPaintGlyph operation, the contours should be retrieved using {@link #FTLoadGlyph} at unscaled,
     * untransformed size. This is because the root transform applied to the graphics context will
     * take care of correct scaling.<br/>
     * Alternatively, to allow hinting of contours, at the time of executing {@link #FTLoadGlyph},
     * the current graphics context transformation matrix can be decomposed into a scaling matrix and a remainder,
     * and {@link #FTLoadGlyph} can be used to retrieve the contours at scaled size. Care must then be taken to blit or
     * clip to the graphics context with taking this remainder transformation into account.
     *
     * @param face A handle to the parent face object.
     * @param base_glyph The glyph index for which to retrieve the root paint table.
     * @param root_transform Specifies whether an initially computed root is returned by the FTPaintTransform
     *                       operation to account for the activated size (see {@link FreeTypeSize#FTActivateSize}) and
     *                       the configured transform and translate (see {@link FreeTypeFace#FTSetTransform}).<br/>
     *                       This root transform is returned before nodes of the glyph graph of the font are returned.
     *                       Subsequent {@link FTCOLRPaint} structures contain unscaled and untransformed values.
     *                       The inserted root transform enables the client application to apply an initial transform
     *                       to its graphics context. When executing subsequent {@link FTCOLRPaint} operations,
     *                       values from {@link FTCOLRPaint} operations will ultimately be correctly scaled because of
     *                       the root transform applied to the graphics context. Use
     *                       {@link FTColorRootTransform#FT_COLOR_INCLUDE_ROOT_TRANSFORM}
     *                       to include the root transform, use {@link FTColorRootTransform#FT_COLOR_NO_ROOT_TRANSFORM} to not include it.
     *                       The latter may be useful when traversing the ‘COLR’ v1 glyph graph and reaching
     *                       a {@link FTPaintColrGlyph}. When recursing into {@link FTPaintColrGlyph} and painting that inline,
     *                       no additional root transform is needed as it has already been applied to
     *                       the graphics context at the beginning of drawing this glyph.
     * @param paint The {@link FTOpaquePaint} object that references the actual paint table.<br/>
     *              The respective actual {@link FTCOLRPaint} object is retrieved via {@link #FTGetPaint}.
     * @return Value 1 if everything is OK. If no color glyph is found, or the root paint could not be retrieved,
     * value 0 gets returned. In case of an error, value 0 is returned also.
     */
    static boolean FTGetColorGlyphPaint(@In MemoryAddress face,
                                        @In int base_glyph,
                                        @Out FTColorRootTransform root_transform,
                                        @In MemoryAddress paint) {
        try {
            return (boolean) GlyphLayerManagement.FT_GET_COLOR_GLYPH_PAINT
                    .invoke(face, base_glyph, root_transform.value(), paint);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Search for a ‘COLR’ v1 clip box for the specified base_glyph and fill the clip_box parameter
     * with the ‘COLR’ v1 ‘ClipBox’ information if one is found.
     *
     * @apiNote To retrieve the clip box in font units, reset scale to units-per-em and remove transforms
     * configured using {@link FreeTypeFace#FTSetTransform}.
     *
     * @param face A handle to the parent face object.
     * @param base_glyph The glyph index for which to retrieve the clip box.
     * @param clip_box The clip box for the requested base_glyph if one is found. The clip box is computed taking scale
     *                 and transformations configured on the FT_Face into account. {@link FTClipBox} contains
     *                 {@link FTVector} values in 26.6 format.
     * @return Value 1 if a clip box is found. If no clip box is found or an error occured, value 0 is returned.
     */
    static boolean FTGetColorGlyphClipBox(@In MemoryAddress face,
                                          @In int base_glyph,
                                          @Out MemoryAddress clip_box) {
        try {
            return (boolean) GlyphLayerManagement.FT_GET_COLOR_GLYPH_CLIP_BOX
                    .invoke(face, base_glyph, clip_box);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Access the layers of a PaintColrLayers table.<br/>
     * If the root paint of a color glyph, or a nested paint of a ‘COLR’ glyph is a PaintColrLayers table,
     * this function retrieves the layers of the PaintColrLayers table.<br/>
     * The {@link FTPaintColrLayers} object contains an {@link FTLayerIterator}, which is used here to
     * iterate over the layers. Each layer is returned as an {@link FTOpaquePaint} object, which then can be used
     * with {@link #FTGetPaint} to retrieve the actual paint object.
     * @param face A handle to the parent face object.
     * @param iterator The {@link FTLayerIterator} from an {@link FTPaintColrLayers} object, for which the layers are to be retrieved.
     *                 The internal state of the iterator is incremented after one call to this function for retrieving one layer.
     * @param paint The {@link FTOpaquePaint} object that references the actual paint table. The respective actual {@link FTCOLRPaint} object
     *              is retrieved via {@link #FTGetPaint}.
     * @return Value 1 if everything is OK. Value 0 gets returned
     * when the paint object can not be retrieved or any other error occurs.
     */
    static boolean FTGetPaintLayers(@In MemoryAddress face,
                                    @In @Out MemoryAddress iterator,
                                    @Out MemoryAddress paint) {
        try {
            return (boolean) GlyphLayerManagement.FT_GET_PAINT_LAYERS
                    .invoke(face, iterator, paint);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * This is an interface to color gradient information in a ‘COLR’ v1 table in OpenType fonts
     * to iteratively retrieve the gradient and solid fill information for colored glyph layers for a specified glyph ID.<br/>
     * https://github.com/googlefonts/colr-gradients-spec
     * @param face A handle to the parent face object.
     * @param color_stop Color index and alpha value for the retrieved color stop.
     * @param iterator The retrieved {@link FTColorStopIterator}, configured on an {@link FTColorLine}, which in turn
     *                 got retrieved via paint information in {@link FTPaintLinearGradient} or {@link FTPaintRadialGradient}.
     * @return Value 1 if everything is OK. If there are no more color stops, value 0 gets returned.
     * In case of an error, value 0 is returned also.
     */
    static boolean FTGetColorlineStops(@In MemoryAddress face,
                                       @Out MemoryAddress color_stop,
                                       @In @Out MemoryAddress iterator) {
        try {
            return (boolean) GlyphLayerManagement.FT_GET_COLOR_LINE_STOPS
                    .invoke(face, color_stop, iterator);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Access the details of a paint using an {@link FTOpaquePaint} opaque paint object,
     * which internally stores the offset to the respective Paint object in the ‘COLR’ table.
     * @param face A handle to the parent face object.
     * @param opaque_paint The opaque paint object for which the underlying {@link FTCOLRPaint} data is to be retrieved.
     * @param paint The specific {@link FTCOLRPaint} object containing information coming from one of the font's Paint* tables.
     * @return Value 1 if everything is OK. Value 0 if no details can be found for this paint or any other error occurred.
     */
    static boolean FTGetPaint(@In MemoryAddress face,
                              @In @Struct MemorySegment opaque_paint,
                              @Out MemoryAddress paint) {
        try {
            return (boolean) GlyphLayerManagement.FT_GET_PAINT
                    .invoke(face, opaque_paint.address(), paint);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Load a glyph into the glyph slot of a face object.
     *
     * @apiNote The loaded glyph may be transformed. See {@link FreeTypeFace#FTSetTransform} for the details.<br/>
     * For subsetted CID-keyed fonts, {@code FT_Err_Invalid_Argument} is returned for
     * invalid CID values (this is, for CID values that don't have a corresponding glyph in the font).
     * See the discussion of the {@link FTFace#FT_FACE_FLAG_CID_KEYED} flag for more details.<br/>
     * If you receive {@code FT_Err_Glyph_Too_Big}, try getting the glyph outline at EM size,
     * then scale it manually and fill it as a graphics operation.
     *
     * @param face        Load a glyph into the glyph slot of a face object.
     * @param glyph_index The index of the glyph in the font file.
     *                    For CID-keyed fonts (either in PS or in CFF format) this argument specifies the CID value.
     * @param load_flags  The index of the glyph in the font file.
     *                    For CID-keyed fonts (either in PS or in CFF format) this argument specifies the CID value.
     * @return The index of the glyph in the font file.
     * For CID-keyed fonts (either in PS or in CFF format) this argument specifies the CID value.
     */
    static int FTLoadGlyph(@In MemoryAddress face, @In int glyph_index, @In int load_flags) {
        try {
            return (int) BaseInterface.FT_LOAD_GLYPH.invoke(face, glyph_index, load_flags);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Retrieve the ASCII name of a given glyph in a face. This only works for those faces where {@link FTFace#FTHasGlyphNames}
     * returns 1.
     *
     * @param face        Retrieve the ASCII name of a given glyph in a face. This only works for those faces where
     *                    {@link FTFace#FTHasGlyphNames} returns 1.
     * @param glyph_index The glyph index.
     * @param buffer_max  The maximum number of bytes available in the buffer.
     * @param buffer      A pointer to a target buffer where the name is copied to.
     * @return A pointer to a target buffer where the name is copied to.
     * @apiNote An error is returned if the face doesn't provide glyph names or if the glyph index is invalid.
     * In all cases of failure, the first byte of buffer is set to 0 to indicate an empty name.<br/>
     * The glyph name is truncated to fit within the buffer if it is too long. The returned string is always
     * zero-terminated.<br/>
     * Be aware that FreeType reorders glyph indices internally so that glyph index 0 always corresponds
     * to the ‘missing glyph’ (called ‘.notdef’).<br/>
     * This function always returns an error if the config macro {@code FT_CONFIG_OPTION_NO_GLYPH_NAMES}
     * is not defined in ftoption.h.
     */
    static int FTGetGlyphName(@In MemoryAddress face, @In int glyph_index, @Out MemorySegment buffer, @In int buffer_max) {
        try {
            return (int) BaseInterface.FT_GET_GLYPH_NAME.invoke(face, glyph_index, buffer.address(), buffer_max);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * A function used to create a new empty glyph image. Note that the created {@link FTGlyph} object must be released
     * with {@link #FTDoneGlyph}.
     *
     * @param library A handle to the FreeType library object.
     * @param format The format of the glyph's image.
     * @param aglyph A handle to the glyph object.
     * @return FreeType error code. 0 means success.
     * */
    static int FTNewGlyph(@In MemoryAddress library, @In int format, @Out MemorySegment aglyph) {
        try {
            return (int) GlyphManagement.FT_NEW_GLYPH.invoke(library, format, aglyph.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * A function used to extract a glyph image from a slot. Note that the created {@link FTGlyph} object must be released
     * with {@link #FTDoneGlyph}.
     *
     * @apiNote Because *aglyph->advance.x and *aglyph->advance.y are 16.16 fixed-point numbers, slot->advance.x
     * and slot->advance.y (which are in 26.6 fixed-point format) must be in the range ]-32768;32768[.
     *
     * @param slot A handle to the source glyph slot.
     * @param aglyph A handle to the glyph object.
     * @return FreeType error code. 0 means success.
     * */
    static int FTGetGlyph(@In MemoryAddress slot, @Out MemorySegment aglyph) {
        try {
            return (int) GlyphManagement.FT_GET_GLYPH.invoke(slot, aglyph.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * A function used to copy a glyph image. Note that the created {@link FTGlyph} object must be released
     * with {@link #FTDoneGlyph}.
     *
     * @param source A handle to the source glyph object.
     * @param target A handle to the target glyph object. 0 in case of error.
     * @return A handle to the target glyph object. 0 in case of error.
     * */
    static int FTGlyphCopy(@In MemoryAddress source, @Out MemoryAddress target) {
        try {
            return (int) GlyphManagement.FT_GLYPH_COPY.invoke(source, target);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Transform a glyph image if its format is scalable.
     *
     * @apiNote The 2x2 transformation matrix is also applied to the glyph's advance vector.
     *
     * @param glyph A handle to the target glyph object.
     * @param matrix A pointer to a 2x2 matrix to apply.
     * @param delta A pointer to a 2d vector to apply. Coordinates are expressed in 1/64th of a pixel.
     * @return FreeType error code (if not 0, the glyph format is not scalable).
     * */
    static int FTGlyphTransform(@In @Out MemoryAddress glyph, @In MemorySegment matrix, @In MemorySegment delta) {
        try {
            return (int) GlyphManagement.FT_GLYPH_TRANSFORM.invoke(glyph, matrix.address(), delta.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return a glyph's ‘control box’. The control box encloses all the outline's points, including Bezier control points.
     * Though it coincides with the exact bounding box for most glyphs, it can be slightly larger in some situations
     * (like when rotating an outline that contains Bezier outside arcs).<br/>
     * Computing the control box is very fast, while getting the bounding box can take much more time as it needs to
     * walk over all segments and arcs in the outline. To get the latter, you can use the ‘ftbbox’ component, which is
     * dedicated to this single task.
     *
     * @apiNote Coordinates are relative to the glyph origin, using the y upwards convention.<br/>
     * If the glyph has been loaded with {@link FreeType#FT_LOAD_NO_SCALE}, bbox_mode must be set to
     * {@link io.github.mmc1234.jfreetype.glyph.FTGlyphBBoxMode#FT_GLYPH_BBOX_UNSCALED} to get unscaled font units in 26.6 pixel format.
     * The value {@link io.github.mmc1234.jfreetype.glyph.FTGlyphBBoxMode#FT_GLYPH_BBOX_SUBPIXELS} is another name for this constant.<br/>
     * If the font is tricky and the glyph has been loaded with {@link FreeType#FT_LOAD_NO_SCALE},
     * the resulting CBox is meaningless. To get reasonable values for the CBox it is necessary to load the glyph
     * at a large ppem value (so that the hinting instructions can properly shift and scale the subglyphs),
     * then extracting the CBox, which can be eventually converted back to font units.<br/>
     * Note that the maximum coordinates are exclusive, which means that one can compute the width and height of
     * the glyph image (be it in integer or 26.6 pixels) as:
     *
     * <pre>{@code
     * dst = alpha * src + (1 - alpha) * dst
     * }</pre><br/>
     *
     * Note also that for 26.6 coordinates, if bbox_mode is set to
     * {@link io.github.mmc1234.jfreetype.glyph.FTGlyphBBoxMode#FT_GLYPH_BBOX_GRIDFIT}, the coordinates will also
     * be grid-fitted, which corresponds to:
     *
     * <pre>{@code
     * VarUtils.mapTo(bbox, (Long l) -> (long) Math.floor(l), FTBBox.X_MIN, FTBBox.Y_MIN);
     * VarUtils.mapTo(bbox, (Long l) -> (long) Math.ceil(l), FTBBox.X_MAX, FTBBox.Y_MAX);
     * }</pre><br/>
     *
     * To get the bbox in pixel coordinates, set bbox_mode to
     * {@link io.github.mmc1234.jfreetype.glyph.FTGlyphBBoxMode#FT_GLYPH_BBOX_TRUNCATE}.<br/>
     * To get the bbox in grid-fitted pixel coordinates, set bbox_mode to
     * {@link io.github.mmc1234.jfreetype.glyph.FTGlyphBBoxMode#FT_GLYPH_BBOX_PIXELS}.
     *
     * @param glyph A handle to the source glyph object.
     * @param bbox_mode The mode that indicates how to interpret the returned bounding box values.
     * @param acbox The glyph coordinate bounding box. Coordinates are expressed in 1/64th of pixels if it is grid-fitted.
     * */
    static void FTGlyphGetCBox(@In MemoryAddress glyph, @In int bbox_mode, @Out MemorySegment acbox) {
        try {
            GlyphManagement.FT_GLYPH_GET_C_BOX.invoke(glyph, bbox_mode, acbox.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Convert a given glyph object to a bitmap glyph object.
     *
     * @apiNote This function does nothing if the glyph format isn't scalable.<br/>
     * The glyph image is translated with the origin vector before rendering.<br/>
     * The first parameter is a pointer to an {@link FTGlyph} handle, that will be replaced by this function (with newly allocated data).
     * Typically, you would use (omitting error handling):
     *
     * <pre>{@code
     * MemorySegment glyph = VarUtils.newSegment(FTGlyph.STRUCT_LAYOUT);
     * MemorySegment glyph_bitmap;
     *
     * int error;
     *
     * // load glyph
     * error = FreeTypeFace.FTLoadChar(VarUtils.starAddress(aface), glyphIndex, FreeTypeFace.FT_LOAD_DEFAULT);
     *
     * // extract glyph image
     * MemorySegment face = VarUtils.star(aface, FTGlyphSlot.STRUCT_LAYOUT);
     * error = FreeTypeGlyph.FTGetGlyph(VarUtils.getAddress(FTFace.GLYPH, face), VarUtils.amp(glyph));
     *
     * // convert to a bitmap (default render mode + destroying old)
     * MemorySegment abitmap;
     * if (VarUtils.getInt(FTGlyph.FORMAT, glyph) != FTGlyphFormat.FT_GLYPH_FORMAT_BITMAP.value()) {
     *   error = FreeTypeGlyph.FTGlyphToBitmap(abitmap = VarUtils.amp(glyph),
     *                          FTRenderMode.FT_RENDER_MODE_NORMAL.value(), 0, true);
     *   if (error) // `glyph' unchanged
     *     ...
     * }
     *
     * // access bitmap content by typecasting
     * glyph_bitmap = VarUtils.star(abitmap, FTBitmapGlyph.STRUCT_LAYOUT);
     *
     * // do funny stuff with it, like blitting/drawing
     * ...
     *
     * // discard glyph image (bitmap or not)
     * FreeTypeGlyph.FTDoneGlyph(glyph);
     * }</pre>
     *
     * Here is another example, again without error handling:
     *
     * <pre>{@code
     * MemorySegment glyphs = VarUtils.newSegmentArray(FTGlyph.STRUCT_LAYOUT, MAX_GLYPHS);
     *
     *
     * ...
     *
     * for ( idx = 0; i < MAX_GLYPHS; i++ )
     *   error = FreeTypeGlyph.FTLoadGlyph(face.address(), idx, FreeTypeFace.FT_LOAD_DEFAULT ) ||
     *                      FreeTypeGlyph.FTGetGlyph(VarUtils.getAddress(FTFace.GLYPH, face),
     *                          VarUtils.amp(VarVarUtils.getAtIndex(glyphs, FTGlyph.STRUCT_LAYOUT, idx)));
     *
     * ...
     *
     * for ( idx = 0; i < MAX_GLYPHS; i++ )
     * {
     *   MemorySegment bitmap = VarVarUtils.getAtIndex(glyphs, FTGlyph.STRUCT_LAYOUT, idx);
     *
     *
     *   ...
     *
     *   // after this call, `bitmap' no longer points into
     *   // the `glyphs' array (and the old value isn't destroyed)
     *   MemorySegment abitmap;
     *   FreeTypeGlyph.FTGlyphToBitmap(abitmap = VarUtils.amp(bitmap), FTRenderMode.FT_RENDER_MODE_MONO.value(), 0, false);
     *   bitmap = VarUtils.star(abitmap, FTBitmapGlyph.STRUCT_LAYOUT);
     *
     *   ...
     *
     *   FreeTypeGlyph.FTDoneGlyph(bitmap);
     * }
     *
     * ...
     *
     * for ( idx = 0; i < MAX_GLYPHS; i++ )
     *   FreeTypeGlyph.FTDoneGlyph(VarVarUtils.getAtIndex(glyphs, FTGlyph.STRUCT_LAYOUT, idx));
     * }</pre><br/>
     *
     * @param the_glyph A pointer to a handle to the target glyph.
     * @param render_mode An enumeration that describes how the data is rendered.
     * @param origin A pointer to a vector used to translate the glyph image before rendering. Can be 0 (if no translation).
     *               The origin is expressed in 26.6 pixels.
     * @param destory A boolean that indicates that the original glyph image should be destroyed by this function.
     *                It is never destroyed in case of error.
     * @return FreeType error code. 0 means success.
     * */
    static int FTGlyphToBitmap(@In @Out MemorySegment the_glyph,
                               @In FTRenderMode render_mode,
                               @In MemoryAddress origin,
                               @In boolean destory) {
        try {
            return (int) GlyphManagement.FT_GLYPH_TO_BITMAP.invoke(the_glyph.address(),
                    render_mode.getAsInt(), origin, destory ? VarUtils.TRUE : VarUtils.FALSE);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Destroy a given glyph.
     * @param glyph A handle to the target glyph object.
     * */
    static void FTDoneGlyph(@In MemoryAddress glyph) {
        try {
            GlyphManagement.FT_DONE_GLYPH.invoke(glyph);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }
}
