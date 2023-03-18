package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.IntField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.StructField;
import jdk.incubator.foreign.MemoryLayout;

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
public final class FTColorLine {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The extend mode at the outer boundaries, see {@link FTPaintExtend}.
     */
    public static final IntField EXTEND;

    /**
     * The {@link FTColorStopIterator} used to enumerate and retrieve the actual {@link FTColorStop}'s.
     */
    public static final StructField COLOR_STOP_ITERATOR;

    static {
        LayoutBuilder builder = new LayoutBuilder("I0", new String[] { "extend", "color_stop_iterator" },
                FTColorStopIterator.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        EXTEND = builder.newInt("extend");
        COLOR_STOP_ITERATOR = builder.newStruct("color_stop_iterator", FTColorStopIterator.STRUCT_LAYOUT);
    }
}
