/*
 * Copyright 2022. mmc1234
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.internal.FreeTypeInternal;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static jdk.incubator.foreign.ValueLayout.ADDRESS;

public class FreeType {
    /* version handles */

    public static final int OK = 0x00;

    /* basic interface handles*/
    public static final int CANNOT_OPEN_RESOURCE = 0x01;
    public static final int UNKNOWN_FILE_FORMAT = 0x02;
    public static final int INVALID_FILE_FORMAT = 0x03;
    public static final int INVALID_VERSION = 0x04;
    public static final int LOWER_MODULE_VERSION = 0x05;
    public static final int INVALID_ARGUMENT = 0x06;
    public static final int UNIMPLEMENTED_FEATURE = 0x07;
    public static final int INVALID_TABLE = 0x08;
    public static final int INVALID_OFFSET = 0x00;
    public static final int ARRAY_TOO_LARGE = 0x0A;
    public static final int MISSING_MODULE = 0x0B;
    public static final int MISSING_PROPERTY = 0x0C;
    public static final int INVALID_GLYPH_INDEX = 0x10;
    public static final int INVALID_CHARACTER_CODE = 0x11;
    public static final int INVALID_GLYPH_FORMAT = 0x12;
    public static final int CANNOT_RENDER_GLYPH = 0x13;
    public static final int INVALID_OUTLINE = 0x14;
    public static final int INVALID_COMPOSITE = 0x15;
    public static final int TOO_MANY_HINTS = 0x16;
    public static final int INVALID_PIXEL_SIZE = 0x17;
    public static final int INVALID_HANDLE = 0x20;
    public static final int INVALID_LIBRARY_HANDLE = 0x21;
    public static final int INVALID_DRIVER_HANDLE = 0x22;
    public static final int INVALID_FACE_HANDLE = 0x23;
    public static final int INVALID_SIZE_HANDLE = 0x24;
    public static final int INVALID_SLOT_HANDLE = 0x25;
    public static final int INVALID_CHARMAP_HANDLE = 0x26;
    public static final int INVALID_CACHE_HANDLE = 0x27;
    public static final int INVALID_STREAM_HANDLE = 0x28;
    public static final int TOO_MANY_DRIVERS = 0x30;
    public static final int TOO_MANY_EXTENSIONS = 0x31;
    public static final int OUT_OF_MEMORY = 0x40;

    /* unicode variation sequences handles*/
    public static final int UNLISTED_OBJECT = 0x41;
    public static final int CANNOT_OPEN_STREAM = 0x51;
    public static final int INVALID_STREAM_SEEK = 0x52;
    public static final int INVALID_STREAM_SKIP = 0x53;
    public static final int INVALID_STREAM_READ = 0x54;

    /* size management handles */
    public static final int INVALID_STREAM_OPERATION = 0x55;
    public static final int INVALID_FRAME_OPERATION = 0x56;
    public static final int NESTED_FRAME_ACCESS = 0x56;


    /* generic errors */
    public static final int INVALID_FRAME_READ = 0x51;
    public static final int RASTER_UNINITIALIZED = 0x60;
    public static final int RASTER_CORRUPTED = 0x61;
    public static final int RASTER_OVERFLOW = 0x62;
    public static final int RASTER_NEGATIVE_HEIGHT = 0x63;
    public static final int TOO_MAY_CACHES = 0x70;
    public static final int INVALID_OPCODE = 0x80;
    public static final int TOO_FEW_ARGUMENTS = 0x81;
    public static final int STACK_OVERFLOW = 0x82;
    public static final int CODE_OVERFLOW = 0x83;
    public static final int BAD_ARGUMENT = 0x84;
    public static final int DIVIDE_BY_ZERO = 0x85;
    public static final int INVALID_REFERENCE = 0x86;

    /* glyph/character errors */
    public static final int DEBUG_OPCODE = 0x87;
    public static final int ENDF_IN_EXEC_STREAM = 0x88;
    public static final int NESTED_DEFS = 0x89;
    public static final int INVALID_CODERANCE = 0x8A;
    public static final int EXECUTION_TOO_LONG = 0x8B;
    public static final int TOO_MANY_FUNCTION_DEFS = 0x8C;
    public static final int TOO_MANY_INSTRUCTION_DEFS = 0x8D;
    public static final int TABLE_MISSING = 0x8E;

    /* handle errors */
    public static final int HORIZ_HEADER_MISSING = 0x8F;
    public static final int LOCATIONS_MISSING = 0x90;
    public static final int NAME_TABLE_MISSING = 0x91;
    public static final int CMAP_TABLE_MISSING = 0x92;
    public static final int HMTX_TABLE_MISSING = 0x93;
    public static final int POST_TABLE_MISSING = 0x94;
    public static final int INVALID_HORIZ_METRICS = 0x95;
    public static final int INVALID_CHARMAP_FORMAT = 0x96;
    public static final int INVALID_PPEM = 0x97;

    /* driver errors */
    public static final int INVALID_VERT_METRICS = 0x98;
    public static final int COULD_NOT_FIND_CONTEXT = 0x99;

    /* memory errors */
    public static final int INVALID_POST_TABLE_FORMAT = 0x9A;
    public static final int INVALID_POST_TABLE = 0x9B;

    /* stream errors */
    public static final int DEF_IN_GLYF_BYTECODE = 0x9C;
    public static final int MISSING_BITMAP = 0x9D;
    public static final int SYNTAX_ERROR = 0xA0;
    public static final int STACK_UNDERFLOW = 0xA1;
    public static final int IGNORE = 0xA2;
    public static final int NO_UNICODE_GLYPH_NAME = 0xA0;
    public static final int GLYPH_TOO_BIG = 0xA0;
    public static final int MISSING_STARTFONT_FIELD = 0xB0;
    /* raster errors */
    public static final int MISSING_FONT_FIELD = 0xB1;
    public static final int MISSING_SIZE_FIELD = 0xB2;
    public static final int MISSING_FONTBOUNDINGBOX_FIELD = 0xB3;
    public static final int MISSING_CHARS_FIELD = 0xB4;

    /* cache errors */
    public static final int MISSING_STARTCHAR_FIELD = 0xB5;

    /* TrueType and SFNT errors */
    public static final int MISSING_ENCODING_FIELD = 0xB6;
    public static final int MISSING_BBX_FIELD = 0xB7;
    public static final int BBX_TOO_BIG = 0xB8;
    public static final int CORRUPTED_FONT_HEADER = 0xB9;
    public static final int CORRUPTED_FONT_GLYPHS = 0xBA;

    /* Load Glyph Flags */
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
    public static final int FT_LOAD_DEFAULT = 0x0;
    /**
     * Don't scale the loaded outline glyph but keep it in font units.<br/>
     * This flag implies {@link #FT_LOAD_NO_HINTING} and {@link #FT_LOAD_NO_BITMAP}, and unsets {@link #FT_LOAD_RENDER}.<br/>
     * If the font is ‘tricky’ (see {@code FT_FACE_FLAG_TRICKY} for more), using {@link #FT_LOAD_NO_SCALE} usually
     * yields meaningless outlines because the subglyphs must be scaled and positioned with hinting instructions.
     * This can be solved by loading the font without {@link #FT_LOAD_NO_SCALE} and setting the character size to font->units_per_EM.
     */
    public static final int FT_LOAD_NO_SCALE = 1;
    /**
     * Disable hinting. This generally generates ‘blurrier’ bitmap glyphs when the glyph are rendered in any of
     * the anti-aliased modes. See also the note below.<br/>
     * This flag is implied by {@link #FT_LOAD_NO_SCALE}.
     */
    public static final int FT_LOAD_NO_HINTING = 1 << 1;
    /**
     * Call {@link #FTRenderGlyph} after the glyph is loaded. By default, the glyph is rendered in {@code FT_RENDER_MODE_NORMAL} mode.
     * This can be overridden by FT_LOAD_TARGET_XXX or {@link #FT_LOAD_MONOCHROME}.<br/>
     * This flag is unset by {@link #FT_LOAD_NO_SCALE}.
     */
    public static final int FT_LOAD_RENDER = 1 << 2;
    /**
     * Ignore bitmap strikes when loading. Bitmap-only fonts ignore this flag.<br/>
     * {@link #FT_LOAD_NO_SCALE} always sets this flag.
     */
    public static final int FT_LOAD_NO_BITMAP = 1 << 3;
    /**
     * Load the glyph for vertical text layout. In particular, {@link FTGlyphSlot#ADVANCE} is set to
     * {@link FTGlyphMetrics#VERT_ADVANCE}.<br/>
     * In case {@code FT_HAS_VERTICAL} doesn't return true, you shouldn't use this flag currently.
     * Reason is that in this case vertical metrics get synthesized, and those values are not always
     * consistent across various font formats.
     */
    public static final int FT_LOAD_VERTICAL_LAYOUT = 1 << 4;
    /**
     * Prefer the auto-hinter over the font's native hinter. See also the note below.
     */
    public static final int FT_LOAD_FORCE_AUTOHINT = 1 << 5;
    /**
     * Ignored. Deprecated.
     */
    public static final int FT_LOAD_CROP_BITMAP = 1 << 6;
    /**
     * Make the font driver perform pedantic verifications during glyph loading and hinting.
     * This is mostly used to detect broken glyphs in fonts. By default, FreeType tries to handle broken fonts also.<br/>
     * In particular, errors from the TrueType bytecode engine are not passed to the application if this flag is not set;
     * this might result in partially hinted or distorted glyphs in case a glyph's bytecode is buggy.
     */
    public static final int FT_LOAD_PEDANTIC = 1 << 7;
    /**
     * Ignored. Deprecated.
     */
    public static final int FT_LOAD_IGNORE_GLOBAL_ADVANCE_WIDTH = 1 << 9;
    /**
     * Don't load composite glyphs recursively. Instead, the font driver fills {@link FTGlyphSlot#NUM_SUBGLYPH} and
     * {@link FTGlyphSlot#SUBGLYPHS}; it also sets {@link FTGlyphSlot#FORMAT} to {@code FT_GLYPH_FORMAT_COMPOSITE}.
     * The description of subglyphs can then be accessed with {@link #FTGetSubGlyphInfo}.<br/>
     * Don't use this flag for retrieving metrics information since some font drivers only return rudimentary data.<br/>
     * This flag implies {@link #FT_LOAD_NO_SCALE} and {@link #FT_LOAD_IGNORE_TRANSFORM}.
     */
    public static final int FT_LOAD_NO_RECURSE = 1 << 10;
    /**
     * Ignore the transform matrix set by {@link #FTSetTransform}.
     */
    public static final int FT_LOAD_IGNORE_TRANSFORM = 1 << 11;
    /**
     * This flag is used with {@link #FT_LOAD_RENDER} to indicate that you want to render an outline glyph
     * to a 1-bit monochrome bitmap glyph, with 8 pixels packed into each byte of the bitmap data.<br/>
     * Note that this has no effect on the hinting algorithm used. You should rather use {@code FT_LOAD_TARGET_MONO}
     * so that the monochrome-optimized hinting algorithm is used.
     */
    public static final int FT_LOAD_MONOCHROME = 1 << 12;
    /**
     * Keep {@link FTGlyphSlot#LINEAR_HORI_ADVANCE} and {@link FTGlyphSlot#LINEAR_VERT_ADVANCE} in font units.
     * See {@link FTGlyphSlot} for details.
     */
    public static final int FT_LOAD_LINEAR_DESIGN = 1 << 13;
    /**
     * Disable the auto-hinter. See also the note below.
     */
    public static final int FT_LOAD_NO_AUTOHINT = 1 << 15;
    /* Bits 16-19 are used by `FT_LOAD_TARGET_` */
    /**
     * Load colored glyphs. There are slight differences depending on the font format.<br/>
     * [Since 2.5] Load embedded color bitmap images. The resulting color bitmaps, if available,
     * will have the {@link io.github.mmc1234.jfreetype.image.FTPixelMode#FT_PIXEL_MODE_BGRA} format,
     * with pre-multiplied color channels. If the flag is not set
     * and color bitmaps are found, they are converted to 256-level gray bitmaps,
     * using the {@link io.github.mmc1234.jfreetype.image.FTPixelMode#FT_PIXEL_MODE_GRAY} format.<br/>
     * [Since 2.10, experimental] If the glyph index contains an entry in the face's ‘COLR’ table with
     * a ‘CPAL’ palette table (as defined in the OpenType specification), make {@link #FTRenderGlyph} provide
     * a default blending of the color glyph layers associated with the glyph index, using the same bitmap format
     * as embedded color bitmap images. This is mainly for convenience; for full control of color layers use
     * {@code FT_Get_Color_Glyph_Layer} and FreeType's color functions like FT_Palette_Select instead of
     * setting {@link #FT_LOAD_COLOR} for rendering so that the client application can handle blending by itself.
     */
    public static final int FT_LOAD_COLOR = 1 << 20;
    /**
     * [Since 2.6.1] Compute glyph metrics from the glyph data, without the use of bundled metrics tables
     * (for example, the ‘hdmx’ table in TrueType fonts). This flag is mainly used by font validating or
     * font editing applications, which need to ignore, verify, or edit those tables.<br/>
     * Currently, this flag is only implemented for TrueType fonts.
     */
    public static final int FT_LOAD_COMPUTE_METRICS = 1 << 21;
    /**
     * [Since 2.7.1] Request loading of the metrics and bitmap image information of a (possibly embedded)
     * bitmap glyph without allocating or copying the bitmap image data itself. No effect if the target glyph
     * is not a bitmap image.<br/>
     * This flag unsets {@link #FT_LOAD_RENDER}.
     */
    public static final int FT_LOAD_BITMAP_METRICS_ONLY = 1 << 22;

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

    static {
        FreeTypeInternal.loadAll();
    }

    // VERSION

    /**
     * Return the version of the FreeType library being used.
     * This is useful when dynamically linking to the library,
     * since one cannot use the macros {@code FREETYPE_MAJOR}, {@code FREETYPE_MINOR}, and {@code FREETYPE_PATCH}.
     *
     * @param library A source library handle.
     * @param amajor  The major version number.
     * @param aminor  The minor version number.
     * @param apatch  The patch version number.
     * @apiNote The reason why this function takes a library argument is because certain programs implement
     * library initialization in a custom way that doesn't use {@link #FTInitFreeType}. In such cases,
     * the library version might not be available before the library object has been created.
     */
    public static void FTLibraryVersion(@In MemoryAddress library, @Out MemorySegment amajor, @Out MemorySegment aminor, @Out MemorySegment apatch) {
        try {
            FreeTypeInternal.FT_LIBRARY_VERSION.invoke(library, amajor.address(), aminor.address(), apatch.address());
        } catch (Throwable e) {
            throw st(e);
        }
    }

    // BASIC INTERFACE


    /**
     * Initialize a new FreeType library object.
     * The set of modules that are registered by this function is determined at build time.
     *
     * @param alibrary A handle to a new library object.
     * @return FreeType error code. 0 means success.
     * @apiNote In case you want to provide your own memory allocating routines, use FTNewLibrary instead,
     * followed by a call to {@code FT_Add_Default_Modules} (or a series of calls to {@code FT_Add_Module})
     * and {@code FT_Set_Default_Properties}.<br/>
     * <p>
     * See the documentation of {@link FTLibrary} and {@link FTFace} for multi-threading issues.<br/>
     * <p>
     * If you need reference-counting (cf. {@code FT_Reference_Library}), use {@code FT_New_Library}
     * and {@code FT_Done_Library}.<br/>
     * <p>
     * If compilation option FT_CONFIG_OPTION_ENVIRONMENT_PROPERTIES is set, this function
     * reads the FREETYPE_PROPERTIES environment variable to control driver properties.
     */
    public static int FTInitFreeType(@Out MemorySegment alibrary) {
        try {
            return (int) FreeTypeInternal.FT_INIT_FREETYPE.invoke(alibrary.address());
        } catch (Throwable e) {
            throw st(e);
        }
    }

    /**
     * Destroy a given FreeType library object and all of its children, including resources, drivers, faces, sizes, etc.
     *
     * @param library A handle to the target library object.
     * @return FreeType error code. 0 means success.
     */
    public static int FTDoneFreeType(@In MemoryAddress library) {
        try {
            return (int) FreeTypeInternal.FT_DONE_FREETYPE.invoke(library);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTNewFace(@In MemoryAddress library, @In MemoryAddress filepathname, @In long face_index, @Out MemorySegment aface) {
        try {
            return (int) FreeTypeInternal.FT_NEW_FACE.invoke(library, filepathname, face_index, aface.address());
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTDoneFace(@In MemoryAddress face) {
        try {
            return (int) FreeTypeInternal.FT_DONE_FACE.invoke(face);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTReferenceFace(@In MemoryAddress face) {
        try {
            return (int) FreeTypeInternal.FT_REFERENCE_FACE.invoke(face);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTNewMemoryFace(@In MemoryAddress library, @In MemoryAddress file_base, @In long file_size, @In long face_index, @Out MemorySegment aface) {
        try {
            return (int) FreeTypeInternal.FT_NEW_MEMORY_FACE.invoke(library, file_base, file_size, face_index, aface.address());
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTFaceProperties(@In MemoryAddress face, @In int num_properties, @In MemoryAddress properties) {
        try {
            return (int) FreeTypeInternal.FT_FACE_PROPERTIES.invoke(face, num_properties, properties);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTOpenFace(@In MemoryAddress library, @In MemoryAddress args, @In long face_index, @Out MemorySegment aface) {
        try {
            return (int) FreeTypeInternal.FT_OPEN_FACE.invoke(library, args, face_index, aface.address());
        } catch (Throwable e) {
            throw st(e);
        }
    }

    /**
     * Call {@link #FTAttachStream} to attach a file.
     *
     * @param face     The target face object.
     * @param filepath The pathname.
     * @return FreeType error code. 0 means success.
     */
    public static int FTAttachFile(@In MemoryAddress face, @In MemoryAddress filepath) {
        try {
            return (int) FreeTypeInternal.FT_ATTACH_FILE.invoke(face, filepath);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTAttachStream(@In MemoryAddress face, @In MemoryAddress parameters) {
        try {
            return (int) FreeTypeInternal.FT_ATTACH_STREAM.invoke(face, parameters);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTSetCharSize(@In MemoryAddress face, @In long char_width, @In long char_height, @In int horz_resolution, @In int vert_resolution) {
        try {
            return (int) FreeTypeInternal.FT_SET_CHAR_SIZE.invoke(face, char_width, char_height, horz_resolution, vert_resolution);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTSetPixelSizes(@In MemoryAddress face, @In int pixel_width, @In int pixel_height) {
        try {
            return (int) FreeTypeInternal.FT_SET_PIXEL_SIZES.invoke(face, pixel_width, pixel_height);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTRequestSize(@In MemoryAddress face, @In MemoryAddress req) {
        try {
            return (int) FreeTypeInternal.FT_REQUEST_SIZE.invoke(face, req);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTSelectSize(@In MemoryAddress face, @In int strike_index) {
        try {
            return (int) FreeTypeInternal.FT_SELECT_SIZE.invoke(face, strike_index);
        } catch (Throwable e) {
            throw st(e);
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
    public static void FTSetTransform(@In MemoryAddress face, @In MemoryAddress matrix, @In MemoryAddress delta) {
        try {
            FreeTypeInternal.FT_SET_TRANSFORM.invoke(face, matrix, delta);
        } catch (Throwable e) {
            throw st(e);
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
    public static void FTGetTransform(@In MemoryAddress face, @Out MemorySegment matrix, @Out MemorySegment delta) {
        try {
            FreeTypeInternal.FT_GET_TRANSFORM.invoke(face, matrix.address(), delta.address());
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTLoadGlyph(@In MemoryAddress face, @In int glyph_index, @In int load_flags) {
        try {
            return (int) FreeTypeInternal.FT_LOAD_GLYPH.invoke(face, glyph_index, load_flags);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTGetCharIndex(@In MemoryAddress face, @In long charcode) {
        try {
            return (int) FreeTypeInternal.FT_GET_CHAR_INDEX.invoke(face, charcode);
        } catch (Throwable e) {
            throw st(e);
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
    public static long FTGetFirstChar(@In MemoryAddress face, @Out MemorySegment agindex) {
        try {
            return (long) FreeTypeInternal.FT_GET_FIRST_CHAR.invoke(face, agindex.address());
        } catch (Throwable e) {
            throw st(e);
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
    public static long FTGetNextChar(@In MemoryAddress face, @In long charcode, @Out MemorySegment agindex) {
        try {
            return (int) FreeTypeInternal.FT_GET_NEXT_CHAR.invoke(face, charcode, agindex.address());
        } catch (Throwable e) {
            throw st(e);
        }
    }

    /**
     * Return the glyph index of a given glyph name.
     *
     * @param face       A handle to the source face object.
     * @param glyph_name A handle to the source face object.
     * @return A handle to the source face object.
     */
    public static int FTGetNameIndex(@In MemoryAddress face, @In MemoryAddress glyph_name) {
        try {
            return (int) FreeTypeInternal.FT_GET_NAME_INDEX.invoke(face, glyph_name);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTLoadChar(@In MemoryAddress face, @In long parameters, @In long load_flags) {
        try {
            return (int) FreeTypeInternal.FT_LOAD_CHAR.invoke(face, parameters, load_flags);
        } catch (Throwable e) {
            throw st(e);
        }
    }

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
     * If {@code FT_RENDER_MODE_NORMAL} is used, a previous call of {@link #FTLoadGlyph} with flag
     * {@code FT_LOAD_COLOR} makes {@link #FTRenderGlyph} provide a default blending of colored glyph layers associated with
     * the current glyph slot (provided the font contains such layers) instead of rendering the glyph slot's outline.
     * This is an experimental feature; see FT_LOAD_COLOR for more information.
     * @apiNote To get meaningful results, font scaling values must be set with functions like {@link #FTSetCharSize}
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
    public static int FTRenderGlyph(@In MemoryAddress slot, @In FTRenderMode render_mode) {
        try {
            return (int) FreeTypeInternal.FT_RENDER_GLYPH.invoke(slot, render_mode.value());
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTGetKerning(@In MemoryAddress face, @In int left_glyph, @In int right_glyph, @In int kern_mode, @Out MemorySegment akerning) {
        try {
            return (int) FreeTypeInternal.FT_GET_KERNING.invoke(face, left_glyph, right_glyph, kern_mode, akerning.address());
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTGetTrackKerning(@In MemoryAddress face, @In long point_size, @In int degree, @Out MemorySegment akerning) {
        try {
            return (int) FreeTypeInternal.FT_GET_TRACK_KERNING.invoke(face, point_size, degree, akerning.address());
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTGetGlyphName(@In MemoryAddress face, @In int glyph_index, @Out MemorySegment buffer, @In int buffer_max) {
        try {
            return (int) FreeTypeInternal.FT_GET_GLYPH_NAME.invoke(face, glyph_index, buffer.address(), buffer_max);
        } catch (Throwable e) {
            throw st(e);
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
    public static MemoryAddress FTGetPostscriptName(@In MemoryAddress face) {
        try {
            // TODO
            return MemoryAddress.ofLong((long) FreeTypeInternal.FT_GET_POSTSCRIPT_NAME.invoke(face));
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTSelectCharmap(@In MemoryAddress face, @In FTEncoding encoding) {
        try {
            return (int) FreeTypeInternal.FT_SELECT_CHARMAP.invoke(face, encoding.value());
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTSetCharmap(@In MemoryAddress face, @In MemoryAddress charmap) {
        try {
            return (int) FreeTypeInternal.FT_SET_CHARMAP.invoke(face, charmap);
        } catch (Throwable e) {
            throw st(e);
        }
    }


    /**
     * Retrieve index of a given charmap.
     *
     * @param charmap Retrieve index of a given charmap.
     * @return Retrieve index of a given charmap.
     */
    public static int FTGetCharmapIndex(@In MemoryAddress charmap) {
        try {
            return (int) FreeTypeInternal.FT_GET_CHARMAP_INDEX.invoke(charmap);
        } catch (Throwable e) {
            throw st(e);
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
    public static short FTGetFSTypeFlags(@In MemoryAddress face) {
        try {
            return (short) FreeTypeInternal.FT_GET_FSTYPE_FLAGS.invoke(face);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTGetSubGlyphInfo(@In MemoryAddress glyph, @In int sub_index, @Out MemorySegment p_index, @Out MemorySegment p_flags, @Out MemorySegment p_arg1, @Out MemorySegment p_arg2, @Out MemorySegment p_transform) {
        try {
            return (int) FreeTypeInternal.FT_GET_SUBGLYPH_INFO.invoke(glyph, sub_index, p_index.address(), p_flags.address(), p_arg1.address(), p_arg2.address(), p_transform.address());
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTFaceGetCharVariantIndex(@In MemoryAddress face, @In long charcode, @In long variantSelector) {
        try {
            return (int) FreeTypeInternal.FT_FACE_GET_CHAR_VARIANT_INDEX.invoke(face, charcode, variantSelector);
        } catch (Throwable e) {
            throw st(e);
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
    public static int FTFaceGetCharVariantIsDefault(@In MemoryAddress face, @In long charcode, @In long variantSelector) {
        try {
            return (int) FreeTypeInternal.FT_FACE_GET_CHAR_VARIANT_IS_DEFAULT.invoke(face, charcode, variantSelector);
        } catch (Throwable e) {
            throw st(e);
        }
    }

    /**
     * Return a zero-terminated list of Unicode variation selectors found in the font.
     *
     * @param face A handle to the source face object.
     * @return A pointer to an array of selector code points, or NULL if there is no valid variation selector cmap subtable.
     * @apiNote The last item in the array is 0; the array is owned by the {@link FTFace} object but can be overwritten or released on the next call to a FreeType function.
     */
    public static int FTFaceGetVariantSelectors(@In MemoryAddress face) {
        try {
            return (int) FreeTypeInternal.FT_FACE_GET_VARIANT_SELECTORS.invoke(face);
        } catch (Throwable e) {
            throw st(e);
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
    public static MemoryAddress FTFaceGetVariantsOfChar(@In MemoryAddress face, @In long charcode) {
        try {
            return MemoryAddress.ofLong((long) FreeTypeInternal.FT_FACE_GET_VARIANTS_OF_CHAR.invoke(face, charcode));
        } catch (Throwable e) {
            throw st(e);
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
    public static MemoryAddress FTFaceGetCharsOfVariant(@In MemoryAddress face, @In long variantSelector) {
        try {
            return MemoryAddress.ofLong((long) FreeTypeInternal.FT_FACE_GET_CHARS_OF_VARIANT.invoke(face, variantSelector));
        } catch (Throwable e) {
            throw st(e);
        }
    }


    // CURRENT

    /**
     * Create a new size object from a given face object.
     *
     * @param face  A handle to a parent face object.
     * @param asize A handle to a new size object.
     * @return A handle to a new size object.
     * @apiNote You need to call {@link #FTActivateSize} in order to select the new size for upcoming calls to {@link #FTSetPixelSizes}, {@link #FTSetCharSize}, {@link #FTLoadGlyph}, {@link #FTLoadChar}, etc.
     */
    public static int FTNewSize(@In MemoryAddress face, @Out MemorySegment asize) {
        try {
            return (int) FreeTypeInternal.FT_NEW_SIZE.invoke(face, asize.address());
        } catch (Throwable e) {
            throw st(e);
        }
    }

    /**
     * Discard a given size object. Note that {@link #FTDoneFace} automatically discards all size objects allocated with {@link #FTNewSize}.
     *
     * @param size A handle to a target size object.
     * @return FreeType error code. 0 means success.
     */
    public static int FTDoneSize(@In MemoryAddress size) {
        try {
            return (int) FreeTypeInternal.FT_DONE_SIZE.invoke(size);
        } catch (Throwable e) {
            throw st(e);
        }
    }


    /**
     * Even though it is possible to create several size objects for a given face (see {@link #FTNewSize} for details), functions like {@link #FTLoadGlyph} or {@link #FTLoadChar} only use the one that has been activated last to determine the ‘current character pixel size’.<br/>
     * <p>
     * This function can be used to ‘activate’ a previously created size object.
     *
     * @param size A handle to a target size object.
     * @return FreeType error code. 0 means success.
     * @apiNote If face is the size's parent face object, this function changes the value of face->size to the input size handle.
     */
    public static int FTActivateSize(@In MemoryAddress size) {
        try {
            return (int) FreeTypeInternal.FT_ACTIVATE_SIZE.invoke(size);
        } catch (Throwable e) {
            throw st(e);
        }
    }

    public static MemoryAddress deRef(MemorySegment v) {
        return v.getAtIndex(ADDRESS, 0);
    }

    private static RuntimeException st(Throwable e) {
        return new RuntimeException(e);
    }


}
