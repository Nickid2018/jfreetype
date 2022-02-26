package io.github.mmc1234.jfreetype.internal;

import java.lang.invoke.MethodHandle;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.load;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.of;

public class UnicodeVariationSequences {
    public static MethodHandle FT_FACE_GET_CHAR_VARIANT_INDEX;
    public static MethodHandle FT_FACE_GET_CHAR_VARIANT_IS_DEFAULT;
    public static MethodHandle FT_FACE_GET_VARIANT_SELECTORS;
    public static MethodHandle FT_FACE_GET_VARIANTS_OF_CHAR;
    public static MethodHandle FT_FACE_GET_CHARS_OF_VARIANT;

    static void loadMethodHandles() {
        FT_FACE_GET_CHAR_VARIANT_INDEX = load("FT_Face_GetCharVariantIndex", of("IALL"));
        FT_FACE_GET_CHAR_VARIANT_IS_DEFAULT = load("FT_Face_GetCharVariantIsDefault", of("IALL"));
        FT_FACE_GET_VARIANT_SELECTORS = load("FT_Face_GetVariantSelectors", of("AA"));
        FT_FACE_GET_VARIANTS_OF_CHAR = load("FT_Face_GetVariantsOfChar", of("AAL"));
        FT_FACE_GET_CHARS_OF_VARIANT = load("FT_Face_GetCharsOfVariant", of("AAL"));
    }
}
