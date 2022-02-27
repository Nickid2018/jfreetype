package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

/**
 * A structure representing a ColorStop value of the ‘COLR’ v1 extensions,
 * see ‘https://github.com/googlefonts/colr-gradients-spec’.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_ColorStop_
 *   {
 *     FT_F2Dot14     stop_offset;
 *     FT_ColorIndex  color;
 *   } FT_ColorStop;
 * }</pre>
 */
public final class FTColorStop {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The stop offset between 0 and 1 along the gradient.
     */
    public static final VarHandle STOP_OFFSET;

    /**
     * The color information for this stop, see {@link FTColorIndex}.
     */
    public static final MethodHandle COLOR;

    static {
        LayoutBuilder builder = new LayoutBuilder("S0", new String[] { "stop_offset", "color" },
                FTColorIndex.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        STOP_OFFSET = builder.primitiveField("stop_offset");
        COLOR = builder.structField("color");
    }
}
