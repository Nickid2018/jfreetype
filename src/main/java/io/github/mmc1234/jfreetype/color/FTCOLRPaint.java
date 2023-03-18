package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.IntField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.util.StructField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * A union object representing format and details of a paint table of a ‘COLR’ v1 font,
 * see ‘https://github.com/googlefonts/colr-gradients-spec’.
 * Use FT_Get_Paint to retrieve a FT_COLR_Paint for an {@link FTOpaquePaint} object.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct  FT_COLR_Paint_
 *   {
 *     FT_PaintFormat format;
 *
 *     union
 *     {
 *       FT_PaintColrLayers      colr_layers;
 *       FT_PaintGlyph           glyph;
 *       FT_PaintSolid           solid;
 *       FT_PaintLinearGradient  linear_gradient;
 *       FT_PaintRadialGradient  radial_gradient;
 *       FT_PaintSweepGradient   sweep_gradient;
 *       FT_PaintTransform       transform;
 *       FT_PaintTranslate       translate;
 *       FT_PaintScale           scale;
 *       FT_PaintRotate          rotate;
 *       FT_PaintSkew            skew;
 *       FT_PaintComposite       composite;
 *       FT_PaintColrGlyph       colr_glyph;
 *     } u;
 *   } FT_COLR_Paint;
 * }</pre>
 */
public final class FTCOLRPaint {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The gradient format for this Paint structure.
     */
    public static final IntField FORMAT;

    /**
     * Union of all paint table types:
     * {@link FTPaintColrLayers}, {@link FTPaintGlyph}, {@link FTPaintSolid}, {@link FTPaintLinearGradient},
     * {@link FTPaintRadialGradient}, {@link FTPaintSweepGradient}, {@link FTPaintTransform}, {@link FTPaintTranslate},
     * {@link FTPaintRotate}, {@link FTPaintSkew}, {@link FTPaintComposite}, {@link FTPaintColrGlyph}
     */
    public static final StructField U;

    static {
        LayoutBuilder builder = new LayoutBuilder("I0", new String[] { "format", "u" },
                FTCOLRPaintUnion.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        FORMAT = builder.newInt("format");
        U = builder.newStruct("u", FTCOLRPaintUnion.STRUCT_LAYOUT);
    }
}
