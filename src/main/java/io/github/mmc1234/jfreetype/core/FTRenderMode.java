package io.github.mmc1234.jfreetype.core;

/**
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef enum  FT_Render_Mode_
 *   {
 *     FT_RENDER_MODE_NORMAL = 0,
 *     FT_RENDER_MODE_LIGHT,
 *     FT_RENDER_MODE_MONO,
 *     FT_RENDER_MODE_LCD,
 *     FT_RENDER_MODE_LCD_V,
 *     FT_RENDER_MODE_SDF,
 *
 *     FT_RENDER_MODE_MAX
 *
 *   } FT_Render_Mode;
 * }</pre>
 */
public enum FTRenderMode {
    ;
    int value;

    public int value() {
        return value;
    }
}
