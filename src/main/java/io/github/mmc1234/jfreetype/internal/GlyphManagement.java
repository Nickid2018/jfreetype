package io.github.mmc1234.jfreetype.internal;

import java.lang.invoke.MethodHandle;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.load;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.of;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.ofVoid;

public class GlyphManagement {
    public static MethodHandle FT_NEW_GLYPH;
    public static MethodHandle FT_GET_GLYPH;
    public static MethodHandle FT_GLYPH_COPY;
    public static MethodHandle FT_GLYPH_TRANSFORM;
    public static MethodHandle FT_GLYPH_GET_C_BOX;
    public static MethodHandle FT_GLYPH_TO_BITMAP;
    public static MethodHandle FT_DONE_GLYPH;
    static void loadMethodHandles() {
        FT_NEW_GLYPH = load("FT_New_Glyph", of("IAIA"));
        FT_GET_GLYPH = load("FT_Get_Glyph", of("IAA"));
        FT_GLYPH_COPY = load("FT_Glyph_Copy", of("IAA"));
        FT_GLYPH_TRANSFORM = load("FT_Glyph_Transform", of("IAAA"));
        FT_GLYPH_GET_C_BOX = load("FT_Glyph_Get_CBox", ofVoid("AIA"));
        FT_GLYPH_TO_BITMAP = load("FT_Glyph_To_Bitmap", of("IAIAI"));
        FT_DONE_GLYPH = load("FT_Done_Glyph", ofVoid("A"));
    }
}
