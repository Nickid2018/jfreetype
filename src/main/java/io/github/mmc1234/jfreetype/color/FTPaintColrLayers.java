package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.StructField;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

/**
 * A structure representing a PaintColrLayers table of a ‘COLR’ v1 font.
 * This table describes a set of layers that are to be composited with composite mode
 * {@link FTCompositeMode#FT_COLR_COMPOSITE_SRC_OVER}. The return value of this function is
 * an {@link FTLayerIterator} initialized so that it can be used with
 * {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTGetPaintLayers} to retrieve the
 * {@link FTOpaquePaint} objects as references to each layer.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintColrLayers_
 *   {
 *     FT_LayerIterator  layer_iterator;
 *   } FT_PaintColrLayers;
 * }</pre>
 */
public final class FTPaintColrLayers {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The layer iterator that describes the layers of this paint.
     */
    public static final StructField LAYER_ITERATOR;

    static {
        LayoutBuilder builder = new LayoutBuilder("0", new String[] { "layer_iterator" },
                FTLayerIterator.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        LAYER_ITERATOR = builder.newStruct("layer_iterator", FTLayerIterator.STRUCT_LAYOUT);
    }
}
