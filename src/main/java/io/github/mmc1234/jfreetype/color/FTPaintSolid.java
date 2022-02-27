package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;

/**
 * A structure representing a PaintSolid value of the ‘COLR’ v1 extensions, see ‘https://github.com/googlefonts/colr-gradients-spec’.
 * Using a PaintSolid value means that the glyph layer filled with this paint is solid-colored and does not contain a gradient.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintSolid_
 *   {
 *     FT_ColorIndex  color;
 *   } FT_PaintSolid;
 * }</pre>
 */
public final class FTPaintSolid {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The color information for this solid paint, see {@link FTColorIndex}.
     */
    public static final MethodHandle COLOR;

    static {
        LayoutBuilder builder = new LayoutBuilder("0", new String[] { "color" }, FTColorIndex.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        COLOR = builder.structField("color");
    }
}
