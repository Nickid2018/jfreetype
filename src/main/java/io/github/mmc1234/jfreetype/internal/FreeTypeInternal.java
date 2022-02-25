package io.github.mmc1234.jfreetype.internal;

import java.lang.invoke.MethodHandle;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.load;
import static io.github.mmc1234.jfreetype.internal.LibraryUtil.loadNative;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.of;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.ofVoid;

public class FreeTypeInternal {
    public static MethodHandle FT_LIBRARY_VERSION;
    public static MethodHandle FT_INIT_FREETYPE;
    public static MethodHandle FT_DONE_FREETYPE;
    public static MethodHandle FT_NEW_FACE;
    public static MethodHandle FT_DONE_FACE;
    public static MethodHandle FT_REFERENCE_FACE;
    public static MethodHandle FT_NEW_MEMORY_FACE;
    public static MethodHandle FT_FACE_PROPERTIES;
    public static MethodHandle FT_OPEN_FACE;
    public static MethodHandle FT_ATTACH_FILE;
    public static MethodHandle FT_ATTACH_STREAM;
    public static MethodHandle FT_SET_PIXEL_SIZES;
    public static MethodHandle FT_SET_CHAR_SIZE;
    public static MethodHandle FT_REQUEST_SIZE;
    public static MethodHandle FT_SELECT_SIZE;
    public static MethodHandle FT_SET_TRANSFORM;
    public static MethodHandle FT_GET_TRANSFORM;
    public static MethodHandle FT_LOAD_GLYPH;
    public static MethodHandle FT_GET_CHAR_INDEX;
    public static MethodHandle FT_GET_FIRST_CHAR;
    public static MethodHandle FT_GET_NEXT_CHAR;
    public static MethodHandle FT_GET_NAME_INDEX;
    public static MethodHandle FT_LOAD_CHAR;
    public static MethodHandle FT_RENDER_GLYPH;
    public static MethodHandle FT_GET_KERNING;
    public static MethodHandle FT_GET_TRACK_KERNING;
    public static MethodHandle FT_GET_GLYPH_NAME;
    public static MethodHandle FT_GET_POSTSCRIPT_NAME;
    public static MethodHandle FT_SELECT_CHARMAP;
    public static MethodHandle FT_SET_CHARMAP;
    public static MethodHandle FT_GET_CHARMAP_INDEX;
    public static MethodHandle FT_GET_FSTYPE_FLAGS;
    public static MethodHandle FT_GET_SUBGLYPH_INFO;
    public static MethodHandle FT_FACE_GET_CHAR_VARIANT_INDEX;
    public static MethodHandle FT_FACE_GET_CHAR_VARIANT_IS_DEFAULT;
    public static MethodHandle FT_FACE_GET_VARIANT_SELECTORS;
    public static MethodHandle FT_FACE_GET_VARIANTS_OF_CHAR;
    public static MethodHandle FT_FACE_GET_CHARS_OF_VARIANT;
    public static MethodHandle FT_NEW_SIZE;
    public static MethodHandle FT_DONE_SIZE;
    public static MethodHandle FT_ACTIVATE_SIZE;

    public static final boolean loadAll() {
        loadNative();

        // INIT VERSION
        FT_LIBRARY_VERSION = load("FT_Library_Version", ofVoid("AAAA"));

        // INIT BASE INTERFACE
        FT_INIT_FREETYPE = load("FT_Init_FreeType", of("IA"));
        FT_DONE_FREETYPE = load("FT_Done_FreeType", of("IA"));
        FT_NEW_FACE = load("FT_New_Face", of("IAALA"));
        FT_DONE_FACE = load("FT_Done_Face", of("IA"));
        FT_REFERENCE_FACE = load("FT_Reference_Face", of("IA"));
        FT_NEW_MEMORY_FACE = load("FT_New_Memory_Face", of("IAALLA"));
        FT_FACE_PROPERTIES = load("FT_Face_Properties", of("IAIA"));
        FT_OPEN_FACE = load("FT_Open_Face", of("IAALA"));
        FT_ATTACH_FILE = load("FT_Attach_File", of("IAA"));
        FT_ATTACH_STREAM = load("FT_Attach_Stream", of("IAA"));
        FT_SET_CHAR_SIZE = load("FT_Set_Char_Size", of("IALLII"));
        FT_SET_PIXEL_SIZES = load("FT_Set_Pixel_Sizes", of("IAII"));
        FT_REQUEST_SIZE = load("FT_Request_Size", of("IAA"));
        FT_SELECT_SIZE = load("FT_Select_Size", of("IAI"));
        // TODO Add enum FT_Size_Request_Type
        FT_SET_TRANSFORM = load("FT_Set_Transform", ofVoid("AAA"));
        FT_GET_TRANSFORM = load("FT_Get_Transform", ofVoid("AAA"));
        FT_LOAD_GLYPH = load("FT_Load_Glyph", of("IAII"));
        FT_GET_CHAR_INDEX = load("FT_Get_Char_Index", of("IAL"));
        FT_GET_FIRST_CHAR = load("FT_Get_First_Char", of("LAA"));
        FT_GET_NEXT_CHAR = load("FT_Get_Next_Char", of("LALA"));
        FT_GET_NAME_INDEX = load("FT_Get_Name_Index", of("IAA"));
        FT_LOAD_CHAR = load("FT_Load_Char", of("IALI"));
        FT_RENDER_GLYPH = load("FT_Render_Glyph", of("IAI"));
        FT_GET_KERNING = load("FT_Get_Kerning", of("IAIIIA"));
        FT_GET_TRACK_KERNING = load("FT_Get_Track_Kerning", of("IALIA"));
        FT_GET_GLYPH_NAME = load("FT_Get_Glyph_Name", of("IAIAI"));
        FT_GET_POSTSCRIPT_NAME = load("FT_Get_Postscript_Name", of("AA"));
        FT_SELECT_CHARMAP = load("FT_Select_Charmap", of("IAI"));
        FT_SET_CHARMAP = load("FT_Set_Charmap", of("IAA"));
        FT_GET_CHARMAP_INDEX = load("FT_Get_Charmap_Index", of("IA"));
        FT_GET_FSTYPE_FLAGS = load("FT_Get_FSType_Flags", of("SA"));
        FT_GET_SUBGLYPH_INFO = load("FT_Get_SubGlyph_Info", of("IAIAAAAA"));

        FT_FACE_GET_CHAR_VARIANT_INDEX = load("FT_Face_GetCharVariantIndex", of("IALL"));
        FT_FACE_GET_CHAR_VARIANT_IS_DEFAULT = load("FT_Face_GetCharVariantIsDefault", of("IALL"));
        FT_FACE_GET_VARIANT_SELECTORS = load("FT_Face_GetVariantSelectors", of("AA"));
        FT_FACE_GET_VARIANTS_OF_CHAR = load("FT_Face_GetVariantsOfChar", of("AAL"));
        FT_FACE_GET_CHARS_OF_VARIANT = load("FT_Face_GetCharsOfVariant", of("AAL"));
        // TODO Glyph Color Management
        // TODO Glyph Layer Management
        // TODO Glyph Management
        // TODO Mac Specific Interface

        FT_NEW_SIZE = load("FT_New_Size", of("IAA"));
        FT_DONE_SIZE = load("FT_Done_Size", of("IA"));
        FT_ACTIVATE_SIZE = load("FT_Done_Size", of("IA"));

        return true;
    }
}
