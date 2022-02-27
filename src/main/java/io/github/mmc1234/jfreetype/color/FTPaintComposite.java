package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

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
    public static final MethodHandle SOURCE_PAINT;

    /**
     * An {@link FTCompositeMode} enum value determining the composition operation.
     */
    public static final VarHandle COMPOSITE_MODE;

    /**
     * An {@link FTOpaquePaint} object referencing the backdrop paint that source_paint is composited onto.
     */
    public static final MethodHandle BACKDROP_PAINT;

    static {
        LayoutBuilder builder = new LayoutBuilder("0I0", new String[] {
                "source_paint", "composite_mode", "backdrop_paint"
        }, FTOpaquePaint.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        SOURCE_PAINT = builder.structField("source_paint");
        COMPOSITE_MODE = builder.primitiveField("composite_mode");
        BACKDROP_PAINT = builder.structField("backdrop_paint");
    }
}
