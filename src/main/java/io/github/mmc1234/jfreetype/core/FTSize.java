package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

/**
 * A handle to an object that models a face scaled to a given character size.
 *
 * @apiNote An {@link FTFace} has one active {@link FTSize} object that is used
 * by functions like {@link FreeTypeGlyph#FTLoadGlyph} to determine
 * the scaling transformation that in turn is used to load and hint glyphs and metrics.<br/>
 * You can use {@link FreeTypeFace#FTSetCharSize}, {@link FreeTypeFace#FTSetPixelSizes},
 * {@link FreeTypeFace#FTRequestSize} or even {@link FreeTypeFace#FTSelectSize} to change
 * the content (i.e., the scaling values) of the active {@link FTSize}.<br/>
 * You can use FTNewSize to create additional size objects for a given {@link FTFace},
 * but they won't be used by other functions until you activate it through FTActivateSize.
 * Only one size can be activated at any given time per face.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct FT_SizeRec_
 *   {
 *     FT_Face          face;      // parent face object
 *     FT_Generic       generic;   // generic pointer for client uses
 *     FT_Size_Metrics  metrics;   // size metrics
 *     FT_Size_Internal internal;
 *   } FT_SizeRec;
 *   typedef struct FT_SizeRec_* FT_Size;
 * }</pre>
 */
public final class FTSize {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * Handle to the parent face object.
     */
    public static final VarHandle FACE;

    /**
     * A typeless pointer, unused by the FreeType library or any of its drivers.
     * It can be used by client applications to link their own data to each size object.
     */
    public static final MethodHandle GENERIC;

    /**
     * Metrics for this size object. This field is read-only.
     */
    public static final MethodHandle METRICS;

    public static final VarHandle INTERNAL;

    static {
        LayoutBuilder builder = new LayoutBuilder("A01A", new String[]{
                "face", "generic", "metrics", "internal"
        }, FTGeneric.STRUCT_LAYOUT, FTSizeMetrics.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        FACE = builder.primitiveField("face");
        INTERNAL = builder.primitiveField("internal");

        GENERIC = builder.structField("generic");
        METRICS = builder.structField("metrics");
    }
}
