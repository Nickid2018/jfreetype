package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.StructField;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;

/**
 * A structure representing a ‘COLR’ v1 PaintTransform paint table.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_PaintTransform_
 *   {
 *     FT_OpaquePaint  paint;
 *     FT_Affine23     affine;
 *   } FT_PaintTransform;
 * }</pre>
 */
public final class FTPaintTransform {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * An opaque paint that is subject to being transformed.
     */
    public static final StructField PAINT;

    /**
     * A 2x3 transformation matrix in {@link FTAffine23} format containing 16.16 fixed-point values.
     */
    public static final StructField AFFINE;

    static {
        LayoutBuilder builder = new LayoutBuilder("01", new String[] { "paint", "affine" },
                FTOpaquePaint.STRUCT_LAYOUT, FTAffine23.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        PAINT = builder.newStruct("paint", FTOpaquePaint.STRUCT_LAYOUT);
        AFFINE = builder.newStruct("affine", FTAffine23.STRUCT_LAYOUT);
    }
}
