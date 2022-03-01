package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.CEnum;

/**
 * An enumeration to specify whether {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGetColorGlyphPaint} is
 * to return a root transform to configure the client's graphics context matrix.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef enum  FT_Color_Root_Transform_
 *   {
 *     FT_COLOR_INCLUDE_ROOT_TRANSFORM,
 *     FT_COLOR_NO_ROOT_TRANSFORM,
 *
 *     FT_COLOR_ROOT_TRANSFORM_MAX
 *   } FT_Color_Root_Transform;
 * }</pre>
 */
public enum FTColorRootTransform implements CEnum {

    /**
     * Do include the root transform as the initial {@link FTCOLRPaint} object.
     */
    FT_COLOR_INCLUDE_ROOT_TRANSFORM,

    /**
     * Do not output an initial root transform.
     */
    FT_COLOR_NO_ROOT_TRANSFORM,

    FT_COLOR_ROOT_TRANSFORM_MAX
}
