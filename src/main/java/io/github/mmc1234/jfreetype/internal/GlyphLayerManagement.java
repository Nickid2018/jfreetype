package io.github.mmc1234.jfreetype.internal;

import java.lang.invoke.MethodHandle;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.load;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.of;

public class GlyphLayerManagement {
    public static MethodHandle FT_GET_COLOR_GLYPH_LAYER;
    public static MethodHandle FT_GET_COLOR_GLYPH_PAINT;
    public static MethodHandle FT_GET_COLOR_GLYPH_CLIP_BOX;
    public static MethodHandle FT_GET_PAINT_LAYERS;
    public static MethodHandle FT_GET_COLOR_LINE_STOPS;
    public static MethodHandle FT_GET_PAINT;

    static void loadMethodHandles() {
        FT_GET_COLOR_GLYPH_LAYER = load("FT_Get_Color_Glyph_Layer", of("BAIIIA"));
        FT_GET_COLOR_GLYPH_PAINT = load("FT_Get_Color_Glyph_Paint", of("BAIIA"));
        FT_GET_COLOR_GLYPH_CLIP_BOX = load("FT_Get_Color_Glyph_ClipBox", of("BAIA"));
        FT_GET_PAINT_LAYERS = load("FT_Get_Paint_Layers", of("BAAA"));
        FT_GET_COLOR_LINE_STOPS = load("FT_Get_Colorline_Stops", of("BAAA"));
        FT_GET_PAINT = load("FT_Get_Paint", of("BAAA"));
    }
}
