package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.internal.BaseInterface;
import io.github.mmc1234.jfreetype.internal.UnicodeVariationSequences;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

public interface FreeTypeFace {
    /**
     * Destroy a given FreeType library object and all of its children, including resources, drivers, faces, sizes, etc.
     *
     * @param library A handle to the target library object.
     * @return FreeType error code. 0 means success.
     */
    static int FTDoneFreeType(@In MemoryAddress library) {
        try {
            return (int) BaseInterface.FT_DONE_FREETYPE.invoke(library);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Call {@link #FTOpenFace} to open a font by its pathname.
     *
     * @param library      A handle to the library resource.
     * @param filepathname A path to the font file.
     * @param face_index   See {@link #FTOpenFace} for a detailed description of this parameter.
     * @param aface        A handle to a new face object. If face_index is greater than or equal to zero, it must be non-NULL.
     * @return FreeType error code. 0 means success.
     * @apiNote The pathname string should be recognizable as such by a standard fopen call on your system;
     * in particular, this means that pathname must not contain null bytes. If that is not sufficient to
     * address all file name possibilities (for example, to handle wide character file names on Windows
     * in UTF-16 encoding) you might use {@link #FTOpenFace} to pass a memory array or a stream object instead.<br/>
     * <p>
     * Use {@link #FTDoneFace} to destroy the created {@link FTFace} object (along with its slot and sizes).
     */
    static int FTNewFace(@In MemoryAddress library, @In MemoryAddress filepathname, @In long face_index, @Out MemorySegment aface) {
        try {
            return (int) BaseInterface.FT_NEW_FACE.invoke(library, filepathname, face_index, aface.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Discard a given face object, as well as all of its child slots and sizes.
     *
     * @param face A handle to a target face object.
     * @return FreeType error code. 0 means success.
     * @apiNote See the discussion of reference counters in the description of {@link #FTReferenceFace}.
     * @see #FTReferenceFace
     */
    static int FTDoneFace(@In MemoryAddress face) {
        try {
            return (int) BaseInterface.FT_DONE_FACE.invoke(face);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * A counter gets initialized to 1 at the time an {@link FTFace} structure is created. This function
     * increments the counter. {@link #FTDoneFace} then only destroys a face if the counter is 1, otherwise
     * it simply decrements the counter.<br/>
     * This function helps in managing life-cycles of structures that reference {@link FTFace} objects.
     *
     * @param face A handle to a target face object.
     * @return FreeType error code. 0 means success.
     */
    static int FTReferenceFace(@In MemoryAddress face) {
        try {
            return (int) BaseInterface.FT_REFERENCE_FACE.invoke(face);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Call {@link #FTOpenFace} to open a font that has been loaded into memory.
     *
     * @param library    A handle to the library resource.
     * @param file_base  A pointer to the beginning of the font data.
     * @param file_size  A pointer to the beginning of the font data.
     * @param face_index See {@link #FTOpenFace} for a detailed description of this parameter.
     * @param aface      A handle to a new face object. If face_index is greater than or equal to zero, it must be non-NULL.
     * @return You must not deallocate the memory before calling {@link #FTDoneFace}.
     */
    static int FTNewMemoryFace(@In MemoryAddress library, @In MemoryAddress file_base, @In long file_size, @In long face_index, @Out MemorySegment aface) {
        try {
            return (int) BaseInterface.FT_NEW_MEMORY_FACE.invoke(library, file_base, file_size, face_index, aface.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Set or override certain (library or module-wide) properties on a face-by-face basis. Useful for
     * finer-grained control and avoiding locks on shared structures (threads can modify their own faces
     * as they see fit).<br/>
     * Contrary to {@code FT_Property_Set}, this function uses {@code FT_Parameter} so that you can pass
     * multiple properties to the target face in one call. Note that only a subset of the available properties
     * can be controlled.<br/>
     * {@code FT_PARAM_TAG_STEM_DARKENING} (stem darkening, corresponding to the property no-stem-darkening
     * provided by the ‘autofit’, ‘cff’, ‘type1’, and ‘t1cid’ modules; see no-stem-darkening).<br/>
     * {@code FT_PARAM_TAG_LCD_FILTER_WEIGHTS} (LCD filter weights, corresponding to function
     * {@code FT_Library_SetLcdFilterWeights}).<br/>
     * {@code FT_PARAM_TAG_RANDOM_SEED} (seed value for the CFF, Type 1, and CID ‘random’ operator, corresponding
     * to the random-seed property provided by the ‘cff’, ‘type1’, and ‘t1cid’ modules; see random-seed).<br/>
     * Pass {@code NULL} as data in {@code FT_Parameter} for a given tag to reset the option and use the library
     * or module default again.
     *
     * @param face           A handle to the source face object.
     * @param num_properties The number of properties that follow.
     * @param properties     A handle to an FT_Parameter array with num_properties elements.
     * @return FreeType error code. 0 means success.
     */
    // TODO FT_Parameter
    static int FTFaceProperties(@In MemoryAddress face, @In int num_properties, @In MemoryAddress properties) {
        try {
            return (int) BaseInterface.FT_FACE_PROPERTIES.invoke(face, num_properties, properties);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Create a face object from a given resource described by {@link FTOpenArgs}.
     *
     * @param library    A handle to the library resource.
     * @param args       A pointer to an {@link FTOpenArgs} structure that must be filled by the caller.
     * @param face_index This field holds two different values. Bits 0-15 are the index of the face in
     *                   the font file (starting with value 0). Set it to 0 if there is only one face in the font file.
     *                   <br/>
     *                   [Since 2.6.1] Bits 16-30 are relevant to GX and OpenType variation fonts only,
     *                   specifying the named instance index for the current face index (starting with value 1;
     *                   value 0 makes FreeType ignore named instances). For non-variation fonts,
     *                   bits 16-30 are ignored. Assuming that you want to access the third named instance in face 4,
     *                   face_index should be set to 0x00030004.
     *                   If you want to access face 4 without variation handling, simply set face_index to value 4.
     *                   <br/>
     *                   {@link #FTOpenFace} and its siblings can be used to quickly check whether
     *                   the font format of a given font resource is supported by FreeType.
     *                   In general, if the face_index argument is negative,
     *                   the function's return value is 0 if the font format is recognized, or non-zero otherwise.
     *                   The function allocates a more or less empty face handle in *aface (if aface isn't NULL);
     *                   the only two useful fields in this special case are face->num_faces and face->style_flags.
     *                   For any negative value of face_index, face->num_faces gives
     *                   the number of faces within the font file.
     *                   For the negative value ‘-(N+1)’ (with ‘N’ a non-negative 16-bit value),
     *                   bits 16-30 in face->style_flags give the number of named instances in face ‘N’ if we have
     *                   a variation font (or zero otherwise).
     *                   After examination, the returned {@link FTFace} structure should be deallocated with
     *                   a call to {@link FreeType#FTDoneFace}.
     * @param aface      A handle to a new face object.
     *                   If face_index is greater than or equal to zero, it must be non-NULL.
     * @return FreeType error code. 0 means success.
     * @apiNote Unlike FreeType 1.x, this function automatically creates a glyph slot for
     * the face object that can be accessed directly through face->glyph.<br/>
     * Each new face object created with this function also owns a default {@link FTSize}
     * object, accessible as face->size.<br/>
     * One {@link FTLibrary} instance can have multiple face objects, this is,
     * {@link #FTOpenFace} and its siblings can be called multiple times using the same
     * library argument.<br/>
     * See the discussion of reference counters in the description of {@link #FTReferenceFace}.<br/>
     * If {@link FTOpenArgs#FT_OPEN_STREAM} is set in args->flags, the stream in args->stream is automatically
     * closed before this function returns any error (including {@code FT_Err_Invalid_Argument}).
     * @see #FTReferenceFace
     */
    static int FTOpenFace(@In MemoryAddress library, @In MemoryAddress args, @In long face_index, @Out MemorySegment aface) {
        try {
            return (int) BaseInterface.FT_OPEN_FACE.invoke(library, args, face_index, aface.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Call {@link #FTAttachStream} to attach a file.
     *
     * @param face     The target face object.
     * @param filepath The pathname.
     * @return FreeType error code. 0 means success.
     */
    static int FTAttachFile(@In MemoryAddress face, @In MemoryAddress filepath) {
        try {
            return (int) BaseInterface.FT_ATTACH_FILE.invoke(face, filepath);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * ‘Attach’ data to a face object. Normally, this is used to read additional information for the face object.
     * For example, you can attach an AFM file that comes with a Type 1 font to get the kerning values and other metrics.
     *
     * @param face       ‘Attach’ data to a face object.
     *                   Normally, this is used to read additional information for the face object.
     *                   the kerning values and other metrics.
     * @param parameters ‘Attach’ data to a face object. Normally, this is used to read additional information for the face object.
     *                   For example, you can attach an AFM file that comes with a Type 1 font to get
     *                   the kerning values and other metrics.
     * @return FreeType error code. 0 means success.
     * @apiNote The meaning of the ‘attach’ (i.e., what really happens when the new file is read) is not fixed by FreeType itself.
     * It really depends on the font format (and thus the font driver).<br/>
     * Client applications are expected to know what they are doing when invoking this function.
     * Most drivers simply do not implement file or stream attachments.
     */
    static int FTAttachStream(@In MemoryAddress face, @In MemoryAddress parameters) {
        try {
            return (int) BaseInterface.FT_ATTACH_STREAM.invoke(face, parameters);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Call {@link #FTRequestSize} to request the nominal size (in points).
     *
     * @param face            A handle to a target face object.
     * @param char_width      The nominal width, in 26.6 fractional points.
     * @param char_height     The nominal height, in 26.6 fractional points.
     * @param horz_resolution The horizontal resolution in dpi.
     * @param vert_resolution The vertical resolution in dpi.
     * @return FreeType error code. 0 means success.
     * @apiNote While this function allows fractional points as input values, resulting ppem value
     * for the given resolution is always rounded to the nearest integer.<br/>
     * If either the character width or height is zero, it is set equal to the other value.<br/>
     * If either the horizontal or vertical resolution is zero, it is set equal to the other value.<br/>
     * A character width or height smaller than 1pt is set to 1pt; if both resolution values are zero,
     * they are set to 72dpi.<br/>
     * Don't use this function if you are using the FreeType cache API.
     */
    static int FTSetCharSize(@In MemoryAddress face, @In long char_width, @In long char_height, @In int horz_resolution, @In int vert_resolution) {
        try {
            return (int) BaseInterface.FT_SET_CHAR_SIZE.invoke(face, char_width, char_height, horz_resolution, vert_resolution);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Call {@link #FTRequestSize} to request the nominal size (in pixels).
     *
     * @param face         A handle to the target face object.
     * @param pixel_width  The nominal width, in pixels.
     * @param pixel_height The nominal height, in pixels.
     * @return FreeType error code. 0 means success.
     * @apiNote You should not rely on the resulting glyphs matching or being constrained to this pixel size.
     * Refer to {@link #FTRequestSize} to understand how requested sizes relate to actual sizes.<br/>
     * Don't use this function if you are using the FreeType cache API.
     */
    static int FTSetPixelSizes(@In MemoryAddress face, @In int pixel_width, @In int pixel_height) {
        try {
            return (int) BaseInterface.FT_SET_PIXEL_SIZES.invoke(face, pixel_width, pixel_height);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Resize the scale of the active {@link FTSize} object in a face.
     *
     * @param face A handle to a target face object.
     * @param req  A pointer to a FT_Size_RequestRec.
     * @return FreeType error code. 0 means success.
     * @apiNote Although drivers may select the bitmap strike matching the request,
     * you should not rely on this if you intend to select a particular bitmap strike.
     * Use {@link #FTSelectSize} instead in that case.<br/>
     * The relation between the requested size and the resulting glyph size is dependent entirely
     * on how the size is defined in the source face.
     * The font designer chooses the final size of each glyph relative to this size.
     * For more information refer to https://www.freetype.org/freetype2/docs/glyphs/glyphs-2.html.<br/>
     * Contrary to {@link #FTSetCharSize}, this function doesn't have special code to normalize
     * zero-valued widths, heights, or resolutions (which lead to errors in most cases).<br/>
     * Don't use this function if you are using the FreeType cache API.
     */
    static int FTRequestSize(@In MemoryAddress face, @In MemoryAddress req) {
        try {
            return (int) BaseInterface.FT_REQUEST_SIZE.invoke(face, req);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Select a bitmap strike.
     * To be more precise, this function sets the scaling factors of
     * the active {@link FTSize} object in a face so that bitmaps from this particular strike
     * are taken by {@link #FTLoadGlyph} and friends.
     *
     * @param face         A handle to a target face object.
     * @param strike_index The index of the bitmap strike in the available_sizes field of {@code FT_FaceRec} structure.
     * @return FreeType error code. 0 means success.
     * @apiNote For bitmaps embedded in outline fonts it is common that only a subset of the available glyphs
     * at a given ppem value is available. FreeType silently uses outlines if there is no bitmap for
     * a given glyph index.<br/>
     * For GX and OpenType variation fonts, a bitmap strike makes sense only if the default instance
     * is active (this is, no glyph variation takes place); otherwise, FreeType simply ignores bitmap
     * strikes. The same is true for all named instances that are different from the default instance.<br/>
     * Don't use this function if you are using the FreeType cache API.
     */
    static int FTSelectSize(@In MemoryAddress face, @In int strike_index) {
        try {
            return (int) BaseInterface.FT_SELECT_SIZE.invoke(face, strike_index);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Set the transformation that is applied to glyph images when they
     * are loaded into a glyph slot through {@link #FTLoadGlyph}.
     *
     * @param face   A handle to the source face object.
     * @param matrix A pointer to the transformation's 2x2 matrix. Use NULL for the identity matrix.
     * @param delta  A pointer to the translation vector. Use NULL for the null vector.
     * @apiNote This function is provided as a convenience, but keep in mind that {@code FT_Matrix} coefficients
     * are only 16.16 fixed-point values, which can limit the accuracy of the results. Using floating-point
     * computations to perform the transform directly in client code instead will always yield better numbers.<br/>
     * The transformation is only applied to scalable image formats after the glyph has been loaded. It means
     * that hinting is unaltered by the transformation and is performed on the character size given in the
     * last call to {@link #FTSetCharSize} or {@link #FTSetPixelSizes}.<br/>
     * <p>
     * Note that this also transforms the face.glyph.advance field, but not the values in face.glyph.metrics.
     */
    static void FTSetTransform(@In MemoryAddress face, @In MemoryAddress matrix, @In MemoryAddress delta) {
        try {
            BaseInterface.FT_SET_TRANSFORM.invoke(face, matrix, delta);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return the transformation that is applied to glyph images when they are loaded into
     * a glyph slot through {@link #FTLoadGlyph}.
     * See {@link #FTSetTransform} for more details.
     *
     * @param face   A handle to the source face object.
     * @param matrix A pointer to a transformation's 2x2 matrix. Set this to NULL if you are not interested in the value.
     * @param delta  A pointer a translation vector. Set this to NULL if you are not interested in the value.
     */
    static void FTGetTransform(@In MemoryAddress face, @Out MemorySegment matrix, @Out MemorySegment delta) {
        try {
            BaseInterface.FT_GET_TRANSFORM.invoke(face, matrix.address(), delta.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Load a glyph into the glyph slot of a face object.
     *
     * @param face        Load a glyph into the glyph slot of a face object.
     * @param glyph_index The index of the glyph in the font file.
     *                    For CID-keyed fonts (either in PS or in CFF format) this argument specifies the CID value.
     * @param load_flags  The index of the glyph in the font file.
     *                    For CID-keyed fonts (either in PS or in CFF format) this argument specifies the CID value.
     * @return The index of the glyph in the font file.
     * For CID-keyed fonts (either in PS or in CFF format) this argument specifies the CID value.
     * @apiNote The loaded glyph may be transformed. See {@link #FTSetTransform} for the details.<br/>
     * For subsetted CID-keyed fonts, {@code FT_Err_Invalid_Argument} is returned for
     * invalid CID values (this is, for CID values that don't have a corresponding glyph in the font).
     * See the discussion of the {@code FT_FACE_FLAG_CID_KEYED} flag for more details.<br/>
     * If you receive {@code FT_Err_Glyph_Too_Big}, try getting the glyph outline at EM size,
     * then scale it manually and fill it as a graphics operation.
     */
    static int FTLoadGlyph(@In MemoryAddress face, @In int glyph_index, @In int load_flags) {
        try {
            return (int) BaseInterface.FT_LOAD_GLYPH.invoke(face, glyph_index, load_flags);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return the glyph index of a given character code. This function uses the currently selected charmap to do the mapping.
     *
     * @param face     A handle to the source face object.
     * @param charcode A handle to the source face object.
     * @return The glyph index. 0 means ‘undefined character code’.
     * @apiNote If you use FreeType to manipulate the contents of font files directly, be aware that the glyph
     * index returned by this function doesn't always correspond to the internal indices used within the file.
     * This is done to ensure that value 0 always corresponds to the ‘missing glyph’. If the first glyph
     * is not named ‘.notdef’, then for Type 1 and Type 42 fonts, ‘.notdef’ will be moved into the glyph ID
     * 0 position, and whatever was there will be moved to the position ‘.notdef’ had. For Type 1 fonts,
     * if there is no ‘.notdef’ glyph at all, then one will be created at index 0 and whatever
     * was there will be moved to the last index – Type 42 fonts are considered invalid under this condition.
     */
    static int FTGetCharIndex(@In MemoryAddress face, @In long charcode) {
        try {
            return (int) BaseInterface.FT_GET_CHAR_INDEX.invoke(face, charcode);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return the first character code in the current charmap of a given face, together with its corresponding glyph index.
     *
     * @param face    A handle to the source face object.
     * @param agindex A handle to the source face object.
     * @return A handle to the source face object.
     * @apiNote You should use this function together with {@link #FTGetNextChar} to parse all character codes
     * available in a given charmap. The code should look like this:
     * // TODO
     *
     * <pre>{@code
     *          long charCode = FTGetFirstChar(face, gindex);
     *          MemorySegment gIndex = MemorySegment.allocateNative(JAVA_INT.byteSize(), scope);
     *          while (gIndex.getAtIndex(JAVA_INT, 0) != 0) {
     *              // do something with (charCode, gIndex) pair ...
     *              charCode = FTGetNextChar(face, charCode, gIndex);
     *          }
     *          }</pre>
     * <p>
     * Be aware that character codes can have values up to 0xFFFFFFFF; this might happen for non-Unicode
     * or malformed cmaps. However, even with regular Unicode encoding, so-called ‘last resort fonts’ (using SFNT
     * cmap format 13, see function {@code FT_Get_CMap_Format}) normally have entries for all Unicode characters
     * up to 0x1FFFFF, which can cause a lot of iterations.<br/>
     * Note that *gindex is set to 0 if the charmap is empty. The result itself can be 0 in two cases:
     * if the charmap is empty or if the value 0 is the first valid character code.
     */
    static long FTGetFirstChar(@In MemoryAddress face, @Out MemorySegment agindex) {
        try {
            return (long) BaseInterface.FT_GET_FIRST_CHAR.invoke(face, agindex.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return the next character code in the current charmap of a given face following the value char_code,
     * as well as the corresponding glyph index.
     *
     * @param face     A handle to the source face object.
     * @param charcode The starting character code.
     * @param agindex  The starting character code.
     * @return The charmap's next character code.
     * @apiNote You should use this function with {@link #FTGetFirstChar} to walk over all character codes
     * available in a given charmap. See the note for that function for a simple code example.<br/>
     * Note that *agindex is set to 0 when there are no more codes in the charmap.
     */
    static long FTGetNextChar(@In MemoryAddress face, @In long charcode, @Out MemorySegment agindex) {
        try {
            return (int) BaseInterface.FT_GET_NEXT_CHAR.invoke(face, charcode, agindex.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return the glyph index of a given glyph name.
     *
     * @param face       A handle to the source face object.
     * @param glyph_name A handle to the source face object.
     * @return A handle to the source face object.
     */
    static int FTGetNameIndex(@In MemoryAddress face, @In MemoryAddress glyph_name) {
        try {
            return (int) BaseInterface.FT_GET_NAME_INDEX.invoke(face, glyph_name);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Load a glyph into the glyph slot of a face object, accessed by its character code.
     *
     * @param face       A handle to a target face object where the glyph is loaded.
     * @param parameters The glyph's character code, according to the current charmap used in the face.
     * @param load_flags A flag indicating what to load for this glyph. The {@code FT_LOAD_XXX} constants can
     *                   be used to control the glyph loading process (e.g., whether the outline should be scaled,
     *                   whether to load bitmaps or not, whether to hint the outline, etc).
     * @return FreeType error code. 0 means success.
     * @apiNote This function simply calls {@link #FTGetCharIndex} and {@link #FTLoadGlyph}.<br/>
     * Many fonts contain glyphs that can't be loaded by this function since its glyph indices
     * are not listed in any of the font's charmaps.<br/>
     * If no active cmap is set up (i.e., face->charmap is zero), the call to {@link #FTGetCharIndex}
     * is omitted, and the function behaves identically to {@link #FTLoadGlyph}.
     */
    static int FTLoadChar(@In MemoryAddress face, @In long parameters, @In long load_flags) {
        try {
            return (int) BaseInterface.FT_LOAD_CHAR.invoke(face, parameters, load_flags);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return the kerning vector between two glyphs of the same face.
     *
     * @param face        Return the kerning vector between two glyphs of the same face.
     * @param left_glyph  The index of the left glyph in the kern pair.
     * @param right_glyph The index of the right glyph in the kern pair.
     * @param kern_mode   See {@link FTKerningMode} for more information. Determines the scale and dimension of the returned kerning vector.
     * @param akerning    The kerning vector. This is either in font units, fractional pixels (26.6 format),
     *                    or pixels for scalable formats, and in pixels for fixed-sizes formats.
     * @return FreeType error code. 0 means success.
     * @apiNote Only horizontal layouts (left-to-right & right-to-left) are supported by this method. Other layouts,
     * or more sophisticated kernings, are out of the scope of this API function – they can be implemented
     * through format-specific interfaces.<br/>
     * Kerning for OpenType fonts implemented in a ‘GPOS’ table is not supported; use {@code FT_HAS_KERNING} to
     * find out whether a font has data that can be extracted with {@link #FTGetKerning}.
     */
    static int FTGetKerning(@In MemoryAddress face, @In int left_glyph, @In int right_glyph, @In int kern_mode, @Out MemorySegment akerning) {
        try {
            return (int) BaseInterface.FT_GET_KERNING.invoke(face, left_glyph, right_glyph, kern_mode, akerning.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Only horizontal layouts (left-to-right & right-to-left) are supported by this method. Other layouts,
     * or more sophisticated kernings, are out of the scope of this API function – they can be implemented
     * through format-specific interfaces.<br/>
     * Kerning for OpenType fonts implemented in a ‘GPOS’ table is not supported; use FT_HAS_KERNING to find out
     * whether a font has data that can be extracted with {@link #FTGetKerning}.<br/>
     *
     * @param face       A handle to a source face object.
     * @param point_size A handle to a source face object.
     * @param degree     The degree of tightness. Increasingly negative values represent tighter track kerning, while increasingly positive values represent looser track kerning. Value zero means no track kerning.
     * @param akerning   The kerning in 16.16 fractional points, to be uniformly applied between all glyphs.
     * @return The kerning in 16.16 fractional points, to be uniformly applied between all glyphs.
     * @apiNote Currently, only the Type 1 font driver supports track kerning, using data from AFM files
     * (if attached with {@link #FTAttachFile} or {@link #FTAttachStream}).<br/>
     * Only very few AFM files come with track kerning data; please refer to Adobe's AFM specification for more details.
     */
    static int FTGetTrackKerning(@In MemoryAddress face, @In long point_size, @In int degree, @Out MemorySegment akerning) {
        try {
            return (int) BaseInterface.FT_GET_TRACK_KERNING.invoke(face, point_size, degree, akerning.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Retrieve the ASCII name of a given glyph in a face. This only works for those faces where {@code FT_HAS_GLYPH_NAMES}
     * (face) returns 1.
     *
     * @param face        Retrieve the ASCII name of a given glyph in a face. This only works for those faces where FT_HAS_GLYPH_NAMES(face) returns 1.
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
     * Retrieve the ASCII PostScript name of a given face, if available. This only works with PostScript, TrueType, and OpenType fonts.
     *
     * @param face A handle to the source face object.
     * @return A pointer to the face's PostScript name. NULL if unavailable.
     * @apiNote The returned pointer is owned by the face and is destroyed with it.<br/>
     * For variation fonts, this string changes if you select a different instance,
     * and you have to call {@code FT_Get_PostScript_Name} again to retrieve it. FreeType follows
     * <a href="https://download.macromedia.com/pub/developer/opentype/tech-notes/5902.AdobePSNameGeneration.html">
     * Adobe TechNote #5902, ‘Generating PostScript Names for Fonts Using OpenType Font Variations’</a>.<br/>
     * [Since 2.9] Special PostScript names for named instances are only returned if the named instance is set with
     * {@code FT_Set_Named_Instance} (and the font has corresponding entries in its ‘fvar’ table).
     * If {@code FT_IS_VARIATION} returns true, the algorithmically derived PostScript name is provided,
     * not looking up special entries for named instances.
     */
    static MemoryAddress FTGetPostscriptName(@In MemoryAddress face) {
        try {
            // TODO
            return MemoryAddress.ofLong((long) BaseInterface.FT_GET_POSTSCRIPT_NAME.invoke(face));
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Select a given charmap by its encoding tag (as listed in freetype.h).
     *
     * @param face     A handle to the source face object.
     * @param encoding A handle to the selected encoding.
     * @return A handle to the selected encoding.
     * @apiNote This function returns an error if no charmap in the face corresponds to the encoding queried here.<br/>
     * Because many fonts contain more than a single cmap for Unicode encoding, this function has some special
     * code to select the one that covers Unicode best (‘best’ in the sense that a UCS-4 cmap is preferred to a
     * UCS-2 cmap). It is thus preferable to {@link #FTSetCharmap} in this case.
     */
    static int FTSelectCharmap(@In MemoryAddress face, @In FTEncoding encoding) {
        try {
            return (int) BaseInterface.FT_SELECT_CHARMAP.invoke(face, encoding.value());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * A handle to the source face object.
     *
     * @param face    A handle to the source face object.
     * @param charmap A handle to the source face object.
     * @return FreeType error code. 0 means success.
     * @apiNote This function returns an error if the charmap is not part of the face
     * (i.e., if it is not listed in the face->charmaps table).<br/>
     * It also fails if an OpenType type 14 charmap is selected (which doesn't map character codes to glyph indices at all).
     */
    static int FTSetCharmap(@In MemoryAddress face, @In MemoryAddress charmap) {
        try {
            return (int) BaseInterface.FT_SET_CHARMAP.invoke(face, charmap);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Retrieve index of a given charmap.
     *
     * @param charmap Retrieve index of a given charmap.
     * @return Retrieve index of a given charmap.
     */
    static int FTGetCharmapIndex(@In MemoryAddress charmap) {
        try {
            return (int) BaseInterface.FT_GET_CHARMAP_INDEX.invoke(charmap);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return the fsType flags for a font.
     *
     * @param face Return the fsType flags for a font.
     * @return The fsType flags, see FT_FSTYPE_XXX.
     * @apiNote Use this function rather than directly reading the fs_type field in the {@code PS_FontInfoRec} structure,
     * which is only guaranteed to return the correct results for Type 1 fonts.
     */
    static short FTGetFSTypeFlags(@In MemoryAddress face) {
        try {
            return (short) BaseInterface.FT_GET_FSTYPE_FLAGS.invoke(face);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return the glyph index of a given character code as modified by the variation selector.
     *
     * @param face            A handle to the source face object.
     * @param charcode        The character code point in Unicode.
     * @param variantSelector The Unicode code point of the variation selector.
     * @return The glyph index. 0 means either ‘undefined character code’, or ‘undefined selector code’, or ‘no variation selector cmap subtable’, or ‘current CharMap is not Unicode’.
     * This function is only meaningful if a) the font has a variation selector cmap sub table, and b) the current charmap has a Unicode encoding.
     * @apiNote If you use FreeType to manipulate the contents of font files directly, be aware that the glyph index returned by this function doesn't always correspond to the internal indices used within the file. This is done to ensure that value 0 always corresponds to the ‘missing glyph’.<br/
     */
    static int FTFaceGetCharVariantIndex(@In MemoryAddress face, @In long charcode, @In long variantSelector) {
        try {
            return (int) UnicodeVariationSequences.FT_FACE_GET_CHAR_VARIANT_INDEX.invoke(face, charcode, variantSelector);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Check whether this variation of this Unicode character is the one to be found in the charmap.
     *
     * @param face            A handle to the source face object.
     * @param charcode        The character codepoint in Unicode.
     * @param variantSelector The Unicode codepoint of the variation selector.
     * @return 1 if found in the standard (Unicode) cmap, 0 if found in the variation selector cmap, or -1 if it is not a variation.
     * @apiNote This function is only meaningful if the font has a variation selector cmap subtable.
     */
    static int FTFaceGetCharVariantIsDefault(@In MemoryAddress face, @In long charcode, @In long variantSelector) {
        try {
            return (int) UnicodeVariationSequences.FT_FACE_GET_CHAR_VARIANT_IS_DEFAULT.invoke(face, charcode, variantSelector);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return a zero-terminated list of Unicode variation selectors found in the font.
     *
     * @param face A handle to the source face object.
     * @return A pointer to an array of selector code points, or NULL if there is no valid variation selector cmap subtable.
     * @apiNote The last item in the array is 0; the array is owned by the {@link FTFace} object but can be overwritten or released on the next call to a FreeType function.
     */
    static int FTFaceGetVariantSelectors(@In MemoryAddress face) {
        try {
            return (int) UnicodeVariationSequences.FT_FACE_GET_VARIANT_SELECTORS.invoke(face);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return a zero-terminated list of Unicode variation selectors found for the specified character code.
     *
     * @param face     A handle to the source face object.
     * @param charcode The character codepoint in Unicode.
     * @return The last item in the array is 0; the array is owned by the {@link FTFace} object but can be overwritten or released on the next call to a FreeType function.
     * @apiNote The last item in the array is 0; the array is owned by the {@link FTFace} object but can be overwritten or released on the next call to a FreeType function.
     */
    static MemoryAddress FTFaceGetVariantsOfChar(@In MemoryAddress face, @In long charcode) {
        try {
            return MemoryAddress.ofLong((long) UnicodeVariationSequences.FT_FACE_GET_VARIANTS_OF_CHAR.invoke(face, charcode));
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Return a zero-terminated list of Unicode character codes found for the specified variation selector.
     *
     * @param face            A handle to the source face object.
     * @param variantSelector The variation selector code point in Unicode.
     * @return A list of all the code points that are specified by this selector (both default and non-default codes are returned) or NULL if there is no valid cmap or the variation selector is invalid.
     * @apiNote The last item in the array is 0; the array is owned by the {@link FTFace} object but can be overwritten or released on the next call to a FreeType function.
     */
    static MemoryAddress FTFaceGetCharsOfVariant(@In MemoryAddress face, @In long variantSelector) {
        try {
            return MemoryAddress.ofLong((long) UnicodeVariationSequences.FT_FACE_GET_CHARS_OF_VARIANT.invoke(face, variantSelector));
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }
}
