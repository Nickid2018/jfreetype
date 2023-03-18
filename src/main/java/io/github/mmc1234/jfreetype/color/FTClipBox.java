package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.image.FTVector;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.struct.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure representing a ‘COLR’ v1 ‘ClipBox’ table. ‘COLR’ v1 glyphs may optionally
 * define a clip box for aiding allocation or defining a maximum drawable region.
 * Use {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGetColorGlyphClipBox} to retrieve it.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct  FT_ClipBox_
 *   {
 *     FT_Vector  bottom_left;
 *     FT_Vector  top_left;
 *     FT_Vector  top_right;
 *     FT_Vector  bottom_right;
 *   } FT_ClipBox;
 * }</pre>
 */
public final class FTClipBox {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The bottom left corner of the clip box as an {@link FTVector} with fixed-point coordinates in 26.6 format.
     */
    public static final StructField BOTTOM_LEFT;

    /**
     * The top left corner of the clip box as an {@link FTVector} with fixed-point coordinates in 26.6 format.
     */
    public static final StructField TOP_LEFT;

    /**
     * The top right corner of the clip box as an {@link FTVector} with fixed-point coordinates in 26.6 format.
     */
    public static final StructField TOP_RIGHT;

    /**
     * The bottom right corner of the clip box as an {@link FTVector} with fixed-point coordinates in 26.6 format.
     */
    public static final StructField BOTTOM_RIGHT;

    static {
        LayoutBuilder builder = new LayoutBuilder("0000", new String[] {
                "bottom_left", "top_left", "top_right", "bottom_right"
        }, FTVector.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        BOTTOM_LEFT = builder.newStruct("bottom_left", FTVector.STRUCT_LAYOUT);
        TOP_LEFT = builder.newStruct("top_left", FTVector.STRUCT_LAYOUT);
        TOP_RIGHT = builder.newStruct("top_right", FTVector.STRUCT_LAYOUT);
        BOTTOM_RIGHT = builder.newStruct("bottom_right", FTVector.STRUCT_LAYOUT);
    }
}
