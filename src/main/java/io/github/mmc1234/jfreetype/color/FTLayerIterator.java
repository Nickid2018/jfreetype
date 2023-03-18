package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.core.FreeTypeGlyph;
import io.github.mmc1234.jfreetype.struct.AddressField;
import io.github.mmc1234.jfreetype.struct.IntField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

/**
 * This iterator object is needed for {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGetColorGlyphLayer}.
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_LayerIterator_
 *   {
 *     FT_UInt   num_layers;
 *     FT_UInt   layer;
 *     FT_Byte*  p;
 *   } FT_LayerIterator;
 * }</pre>
 */
public final class FTLayerIterator {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The number of glyph layers for the requested glyph index. Will be set
     * by {@link FreeTypeGlyph#FTGetColorGlyphLayer}.
     */
    public static final IntField NUM_LAYERS;

    /**
     * The current layer. Will be set by {@link FreeTypeGlyph#FTGetColorGlyphLayer}.
     */
    public static final IntField LAYER;

    /**
     * An opaque pointer into ‘COLR’ table data. The caller must set this to NULL before the first call of
     * {@link FreeTypeGlyph#FTGetColorGlyphLayer}.
     */
    public static final AddressField P;

    static {
        LayoutBuilder builder = new LayoutBuilder("IIA", new String[] { "num_layers", "layer", "p" });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        NUM_LAYERS = builder.newInt("num_layers");
        LAYER = builder.newInt("layer");
        P = builder.newAddress("p");
    }
}
