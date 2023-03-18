package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.struct.IntField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.struct.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A structure representing a ‘COLR'v1 PaintComposite paint table.
 * Used for compositing two paints in a 'COLR’ v1 directed acycling graph.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintComposite_
 *   {
 *     FT_OpaquePaint     source_paint;
 *     FT_Composite_Mode  composite_mode;
 *     FT_OpaquePaint     backdrop_paint;
 *   } FT_PaintComposite;
 * }</pre>
 */
public final class FTPaintComposite {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * An {@link FTOpaquePaint} object referencing the source that is to be composited.
     */
    public static final StructField SOURCE_PAINT;

    /**
     * An {@link FTCompositeMode} enum value determining the composition operation.
     */
    public static final IntField COMPOSITE_MODE;

    /**
     * An {@link FTOpaquePaint} object referencing the backdrop paint that source_paint is composited onto.
     */
    public static final StructField BACKDROP_PAINT;

    static {
        LayoutBuilder builder = new LayoutBuilder("0I0", new String[] {
                "source_paint", "composite_mode", "backdrop_paint"
        }, FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        SOURCE_PAINT = builder.newStruct("source_paint", FTOpaquePaint.STRUCT_LAYOUT);
        COMPOSITE_MODE = builder.newInt("composite_mode");
        BACKDROP_PAINT = builder.newStruct("backdrop_paint", FTOpaquePaint.STRUCT_LAYOUT);
    }
}
