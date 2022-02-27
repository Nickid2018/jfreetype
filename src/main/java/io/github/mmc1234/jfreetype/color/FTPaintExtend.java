package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.CEnum;

/**
 * An enumeration representing the ‘Extend’ mode of the ‘COLR’ v1 extensions,
 * see ‘https://github.com/googlefonts/colr-gradients-spec’.
 * It describes how the gradient fill continues at the other boundaries.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef enum FT_PaintExtend_
 *   {
 *     FT_COLR_PAINT_EXTEND_PAD     = 0,
 *     FT_COLR_PAINT_EXTEND_REPEAT  = 1,
 *     FT_COLR_PAINT_EXTEND_REFLECT = 2
 *   } FT_PaintExtend;
 * }</pre>
 */
public enum FTPaintExtend implements CEnum {

    FT_COLR_PAINT_EXTEND_PAD,
    FT_COLR_PAINT_EXTEND_REPEAT,
    FT_COLR_PAINT_EXTEND_REFLECT
}
