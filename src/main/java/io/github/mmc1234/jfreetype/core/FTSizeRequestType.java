package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.CEnum;

/**
 * An enumeration type that lists the supported size request types, i.e.,
 * what input size (in font units) maps to the requested output size
 * (in pixels, as computed from the arguments of {@link FTSizeRequest}).
 *
 * @apiNote The above descriptions only apply to scalable formats.
 * For bitmap formats, the behaviour is up to the driver.<br/>
 * See the note section of {@link FTSizeMetrics} if you wonder
 * how size requesting relates to scaling values.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef enum  FT_Size_Request_Type_
 *   {
 *     FT_SIZE_REQUEST_TYPE_NOMINAL,
 *     FT_SIZE_REQUEST_TYPE_REAL_DIM,
 *     FT_SIZE_REQUEST_TYPE_BBOX,
 *     FT_SIZE_REQUEST_TYPE_CELL,
 *     FT_SIZE_REQUEST_TYPE_SCALES,
 *
 *     FT_SIZE_REQUEST_TYPE_MAX
 *
 *   } FT_Size_Request_Type;
 * }</pre>
 */
public enum FTSizeRequestType implements CEnum {

    /**
     * The nominal size. {@link FTFace#UNITS_PER_EM} is used to determine both scaling values.<br/>
     * This is the standard scaling found in most applications. In particular, use this size request type
     * for TrueType fonts if they provide optical scaling or something similar. Note, however, that
     * {@link FTFace#UNITS_PER_EM} is a rather abstract value which bears no relation to the actual size
     * of the glyphs in a font.
     */
    FT_SIZE_REQUEST_TYPE_NOMINAL,

    /**
     * The real dimension. The sum of the {@link FTFace#ASCENDER} and (minus of) {@link FTFace#DESCENDER} is used
     * to determine both scaling values.
     */
    FT_SIZE_REQUEST_TYPE_REAL_DIM,

    /**
     * The font bounding box. The width and height of {@link FTFace#BBOX} are used to determine
     * the horizontal and vertical scaling value, respectively.
     */
    FT_SIZE_REQUEST_TYPE_BBOX,

    /**
     * The {@link FTFace#MAX_ADVANCE_WIDTH} is used to determine the horizontal scaling value;
     * the vertical scaling value is determined the same way as {@link #FT_SIZE_REQUEST_TYPE_REAL_DIM} does.
     * Finally, both scaling values are set to the smaller one. This type is useful if you want to specify
     * the font size for, say, a window of a given dimension and 80x24 cells.
     */
    FT_SIZE_REQUEST_TYPE_CELL,

    /**
     * Specify the scaling values directly.
     */
    FT_SIZE_REQUEST_TYPE_SCALES,

    FT_SIZE_REQUEST_TYPE_MAX;

    @Override
    public int value() {
        return ordinal();
    }
}
