package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

/**
 * A structure representing a ColorLine value of the ‘COLR’ v1 extensions,
 * see ‘https://github.com/googlefonts/colr-gradients-spec’. It describes a list of color stops along the defined gradient.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_ColorLine_
 *   {
 *     FT_PaintExtend        extend;
 *     FT_ColorStopIterator  color_stop_iterator;
 *   } FT_ColorLine;
 * }</pre>
 */
public class FTColorLine {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The extend mode at the outer boundaries, see {@link FTPaintExtend}.
     */
    public static final VarHandle EXTEND;

    /**
     * The {@link FTColorStopIterator} used to enumerate and retrieve the actual {@link FTColorStop}'s.
     */
    public static final MethodHandle COLOR_STOP_ITERATOR;

    static {
        LayoutBuilder builder = new LayoutBuilder("I0", new String[] { "extend", "color_stop_iterator" },
                FTColorStopIterator.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        EXTEND = builder.primitiveField("extend");
        COLOR_STOP_ITERATOR = builder.structField("color_stop_iterator");
    }
}
