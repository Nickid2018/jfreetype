package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;

/**
 * See {@link FTCOLRPaint}
 */
public final class FTCOLRPaintUnion {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    public static final MethodHandle COLR_LAYERS;
    public static final MethodHandle GLYPH;
    public static final MethodHandle SOLID;
    public static final MethodHandle LINEAR_GRADIENT;
    public static final MethodHandle RADIAL_GRADIENT;
    public static final MethodHandle SWEEP_GRADIENT;
    public static final MethodHandle TRANSFORM;
    public static final MethodHandle TRANSLATE;
    public static final MethodHandle SCALE;
    public static final MethodHandle ROTATE;
    public static final MethodHandle SKEW;
    public static final MethodHandle COMPOSITE;
    public static final MethodHandle COLR_GLYPH;

    static {
        LayoutBuilder builder = new LayoutBuilder("0123456789abc", new String[] {
                "colr_layers", "glyph", "solid", "linear_gradient", "radial_gradient",
                "sweep_gradient", "transform", "translate", "scale", "rotate", "skew",
                "composite", "colr_glyph"
        }, true,
                FTPaintColrLayers.STRUCT_LAYOUT, FTPaintGlyph.STRUCT_LAYOUT, FTPaintSolid.STRUCT_LAYOUT,
                FTPaintLinearGradient.STRUCT_LAYOUT, FTPaintRadialGradient.STRUCT_LAYOUT, FTPaintSweepGradient.STRUCT_LAYOUT,
                FTPaintTransform.STRUCT_LAYOUT, FTPaintTranslate.STRUCT_LAYOUT, FTPaintScale.STRUCT_LAYOUT,
                FTPaintRotate.STRUCT_LAYOUT, FTPaintSkew.STRUCT_LAYOUT, FTPaintComposite.STRUCT_LAYOUT,
                FTPaintColrGlyph.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        COLR_LAYERS = builder.structField("colr_layers");
        GLYPH = builder.structField("glyph");
        SOLID = builder.structField("solid");
        LINEAR_GRADIENT = builder.structField("linear_gradient");
        RADIAL_GRADIENT = builder.structField("radial_gradient");
        SWEEP_GRADIENT = builder.structField("sweep_gradient");
        TRANSFORM = builder.structField("transform");
        TRANSLATE = builder.structField("translate");
        SCALE = builder.structField("scale");
        ROTATE = builder.structField("rotate");
        SKEW = builder.structField("skew");
        COMPOSITE = builder.structField("composite");
        COLR_GLYPH = builder.structField("colr_glyph");
    }
}
