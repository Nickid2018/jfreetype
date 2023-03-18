package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.image.FTVector;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.struct.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct  FT_PaintLinearGradient_
 *   {
 *     FT_ColorLine  colorline;
 *
 *     // Potentially expose those as x0, y0 etc
 *     FT_Vector p0;
 *     FT_Vector p1;
 *     FT_Vector p2;
 *   }FT_PaintLinearGradient;
 * }</pre>
 */
public final class FTPaintLinearGradient {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The {@link FTColorLine} information for this paint, i.e., the list of color stops along the gradient.
     */
    public static final StructField COLORLINE;

    /**
     * The starting point of the gradient definition in font units represented as a 16.16 fixed-point {@link FTVector}.
     */
    public static final StructField P0;

    /**
     * The end point of the gradient definition in font units represented as a 16.16 fixed-point {@link FTVector}.
     */
    public static final StructField P1;

    /**
     * Optional point p2 to rotate the gradient in font units represented as a 16.16 fixed-point {@link FTVector}.
     * Otherwise equal to p0.
     */
    public static final StructField P2;

    static {
        LayoutBuilder builder = new LayoutBuilder("0111", new String[] {
                "colorline", "p0", "p1", "p2"
        }, FTColorLine.STRUCT_LAYOUT, FTVector.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        COLORLINE = builder.newStruct("colorline", FTColorLine.STRUCT_LAYOUT);
        P0 = builder.newStruct("p0", FTVector.STRUCT_LAYOUT);
        P1 = builder.newStruct("p1", FTVector.STRUCT_LAYOUT);
        P2 = builder.newStruct("p2", FTVector.STRUCT_LAYOUT);
    }
}
