package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.AddressField;
import io.github.mmc1234.jfreetype.util.IntField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.ShortField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A handle to a character map (usually abbreviated to ‘charmap’).
 * A charmap is used to translate character codes in a given encoding into glyph indexes for
 * its parent's face. Some font formats may provide several charmaps per font.<br/>
 * Each face object owns zero or more charmaps,
 * but only one of them can be ‘active’,
 * providing the data used by {@link FreeTypeFace#FTGetCharIndex} or {@link FreeTypeFace#FTLoadChar}.<br/>
 * The list of available charmaps in a face is available through
 * the face->numcharmaps and face->charmaps fields of {@link FTFace}.<br/>
 * The currently active charmap is available as face->charmap.
 * You should call {@link FreeTypeFace#FTSetCharmap} to change it.
 *
 * @apiNote When a new face is created (either through {@link FreeTypeFace#FTNewFace} or {@link FreeTypeFace#FTOpenFace}),
 * the library looks for a Unicode charmap within
 * the list and automatically activates it.
 * If there is no Unicode charmap, FreeType doesn't set an ‘active’ charmap.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct FT_CharMapRec_
 *   {
 *     FT_Face      face;
 *     FT_Encoding  encoding;
 *     FT_UShort    platform_id;
 *     FT_UShort    encoding_id;
 *   } FT_CharMapRec;
 * }</pre>
 */
public final class FTCharMap {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * A handle to the parent face object.
     */
    public static final AddressField FACE;

    /**
     * An {@link FTEncoding} tag identifying the charmap. Use this with {@link FreeTypeFace#FTSelectCharmap}.
     */
    public static final IntField ENCODING;

    /**
     * An ID number describing the platform for the following encoding ID.
     * This comes directly from the TrueType specification and gets emulated for other formats.
     */
    public static final ShortField PLATFORM_ID;

    /**
     * A platform-specific encoding number. This also comes from the TrueType specification and gets emulated similarly.
     */
    public static final ShortField ENCODING_ID;

    static {
        LayoutBuilder builder = new LayoutBuilder("AISS", new String[]{
                "face", "encoding", "platform_id", "encoding_id"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        FACE = builder.newAddress("face");
        ENCODING = builder.newInt("encoding");
        PLATFORM_ID = builder.newShort("platform_id");
        ENCODING_ID = builder.newShort("encoding_id");
    }
}
