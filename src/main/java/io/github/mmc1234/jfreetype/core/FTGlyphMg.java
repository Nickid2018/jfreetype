package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.glyph.FTGlyph;
import io.github.mmc1234.jfreetype.internal.GlyphManagement;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

public interface FTGlyphMg {

    /**
     * A function used to create a new empty glyph image. Note that the created {@link FTGlyph} object must be released with {@link FTGlyphMg#FTDoneGlyph}.
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
     * A function used to extract a glyph image from a slot. Note that the created {@link FTGlyph} object must be released with {@link FTGlyphMg#FTDoneGlyph}.
     *
     * @apiNote Because *aglyph->advance.x and *aglyph->advance.y are 16.16 fixed-point numbers, slot->advance.x and slot->advance.y (which are in 26.6 fixed-point format) must be in the range ]-32768;32768[.
     *
     * @param slot A handle to the source glyph slot.
     * @param aglyph A handle to the glyph object.
     * @return FreeType error code. 0 means success.
     * */
    static int FTGetGlyph(@In MemoryAddress slot, @In MemorySegment aglyph) {
        try {
            return (int) GlyphManagement.FT_GET_GLYPH.invoke(slot, aglyph.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * A function used to copy a glyph image. Note that the created {@link FTGlyph} object must be released with {@link FTGlyphMg#FTDoneGlyph}.
     *
     * @param source A handle to the source glyph object.
     * @param target A handle to the target glyph object. 0 in case of error.
     * @return A handle to the target glyph object. 0 in case of error.
     * */
    static int FTGlyphCopy(@In MemoryAddress source, @In MemoryAddress target) {
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
    static int FTGlyphTransform(@In MemoryAddress glyph, @In MemorySegment matrix, @In MemorySegment delta) {
        try {
            return (int) GlyphManagement.FT_GLYPH_TRANSFORM.invoke(glyph, matrix.address(), delta.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return a glyph's ‘control box’. The control box encloses all the outline's points, including Bezier control points. Though it coincides with the exact bounding box for most glyphs, it can be slightly larger in some situations (like when rotating an outline that contains Bezier outside arcs).<br/>
     * Computing the control box is very fast, while getting the bounding box can take much more time as it needs to walk over all segments and arcs in the outline. To get the latter, you can use the ‘ftbbox’ component, which is dedicated to this single task.
     *
     * @apiNote Coordinates are relative to the glyph origin, using the y upwards convention.<br/>
     * If the glyph has been loaded with {@link FreeType#FT_LOAD_NO_SCALE}, bbox_mode must be set to {@code FT_GLYPH_BBOX_UNSCALED} to get unscaled font units in 26.6 pixel format. The value {@code FT_GLYPH_BBOX_SUBPIXELS} is another name for this constant.<br/>
     * If the font is tricky and the glyph has been loaded with {@link FreeType#FT_LOAD_NO_SCALE}, the resulting CBox is meaningless. To get reasonable values for the CBox it is necessary to load the glyph at a large ppem value (so that the hinting instructions can properly shift and scale the subglyphs), then extracting the CBox, which can be eventually converted back to font units.<br/>
     * Note that the maximum coordinates are exclusive, which means that one can compute the width and height of the glyph image (be it in integer or 26.6 pixels) as:<br/><br/>
     * <pre>{@code
     * dst = alpha * src + (1 - alpha) * dst
     * }</pre><br/>
     * Note also that for 26.6 coordinates, if bbox_mode is set to {@code FT_GLYPH_BBOX_GRIDFIT}, the coordinates will also be grid-fitted, which corresponds to:<br/><br/>
     * <pre>{@code
     * bbox.xMin = FLOOR(bbox.xMin);
     * bbox.yMin = FLOOR(bbox.yMin);
     * bbox.xMax = CEILING(bbox.xMax);
     * bbox.yMax = CEILING(bbox.yMax);
     * }</pre><br/>
     * To get the bbox in pixel coordinates, set bbox_mode to {@code FT_GLYPH_BBOX_TRUNCATE}.<br/>
     * To get the bbox in grid-fitted pixel coordinates, set bbox_mode to {@code FT_GLYPH_BBOX_PIXELS}.
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
     * The first parameter is a pointer to an {@link FTGlyph} handle, that will be replaced by this function (with newly allocated data). Typically, you would use (omitting error handling):<br/><br/>
     * <pre>{@code
     * FT_Glyph        glyph;
     * FT_BitmapGlyph  glyph_bitmap;
     *
     *
     * // load glyph
     * error = FT_Load_Char( face, glyph_index, FT_LOAD_DEFAULT );
     *
     * // extract glyph image
     * error = FT_Get_Glyph( face->glyph, &glyph );
     *
     * // convert to a bitmap (default render mode + destroying old)
     * if ( glyph->format != FT_GLYPH_FORMAT_BITMAP )
     * {
     *   error = FT_Glyph_To_Bitmap( &glyph, FT_RENDER_MODE_NORMAL,
     *                                   0, 1 );
     *   if ( error ) // `glyph' unchanged
     *     ...
     * }
     *
     * // access bitmap content by typecasting
     * glyph_bitmap = (FT_BitmapGlyph)glyph;
     *
     * // do funny stuff with it, like blitting/drawing
     * ...
     *
     * // discard glyph image (bitmap or not)
     * FT_Done_Glyph( glyph );
     * }</pre><br/>
     * Here is another example, again without error handling:<br/><br/>
     * <pre>{@code
     * FT_Glyph  glyphs[MAX_GLYPHS]
     *
     *
     * ...
     *
     * for ( idx = 0; i < MAX_GLYPHS; i++ )
     *   error = FT_Load_Glyph( face, idx, FT_LOAD_DEFAULT ) ||
     *           FT_Get_Glyph ( face->glyph, &glyphs[idx] );
     *
     * ...
     *
     * for ( idx = 0; i < MAX_GLYPHS; i++ )
     * {
     *   FT_Glyph  bitmap = glyphs[idx];
     *
     *
     *   ...
     *
     *   // after this call, `bitmap' no longer points into
     *   // the `glyphs' array (and the old value isn't destroyed)
     *   FT_Glyph_To_Bitmap( &bitmap, FT_RENDER_MODE_MONO, 0, 0 );
     *
     *   ...
     *
     *   FT_Done_Glyph( bitmap );
     * }
     *
     * ...
     *
     * for ( idx = 0; i < MAX_GLYPHS; i++ )
     *   FT_Done_Glyph( glyphs[idx] );
     * }</pre><br/>
     *
     * @param the_glyph A pointer to a handle to the target glyph.
     * @param render_mode An enumeration that describes how the data is rendered.
     * @param origin A pointer to a vector used to translate the glyph image before rendering. Can be 0 (if no translation). The origin is expressed in 26.6 pixels.
     * @param destory A boolean that indicates that the original glyph image should be destroyed by this function. It is never destroyed in case of error.
     * @return FreeType error code. 0 means success.
     * */
    static int FTGlyphToBitmap(@In @Out MemorySegment the_glyph,
                               @In FTRenderMode render_mode,
                               @In MemoryAddress origin,
                               boolean destory) {
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
