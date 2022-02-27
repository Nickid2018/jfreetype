package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * This structure models a BGRA color value of a ‘CPAL’ palette entry.<br/>
 * The used color space is sRGB; the colors are not pre-multiplied, and alpha values must be explicitly set.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_Color_
 *   {
 *     FT_Byte  blue;
 *     FT_Byte  green;
 *     FT_Byte  red;
 *     FT_Byte  alpha;
 *   } FT_Color;
 * }</pre>
 */
public final class FTColor {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * Blue value.
     */
    public static final VarHandle BLUE;

    /**
     * Green value.
     */
    public static final VarHandle GREEN;

    /**
     * Red value.
     */
    public static final VarHandle RED;

    /**
     * Alpha value, giving the red, green, and blue color's opacity.
     */
    public static final VarHandle ALPHA;

    static {
        LayoutBuilder builder = new LayoutBuilder("BBBB", new String[] {
                "blue", "green", "red", "alpha"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        BLUE = builder.primitiveField("blue");
        GREEN = builder.primitiveField("green");
        RED = builder.primitiveField("red");
        ALPHA = builder.primitiveField("alpha");
    }
}
