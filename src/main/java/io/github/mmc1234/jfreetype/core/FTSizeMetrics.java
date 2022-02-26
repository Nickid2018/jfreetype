package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * The size metrics structure gives the metrics of a size object.
 *
 * @apiNote The scaling values, if relevant, are determined first during a size changing operation.
 * The remaining fields are then set by the driver. For scalable formats, they are usually set to
 * scaled values of the corresponding fields in {@link FTFace}. Some values like ascender or descender
 * are rounded for historical reasons; more precise values (for outline fonts) can be derived
 * by scaling the corresponding {@link FTFace} values manually, with code similar to the following.
 *
 * <pre>{@code
 *              scaled_ascender = FTMulFix(VarHandleUtils.getShort(FTFace.ASCENDER, face),
 *                                         VarHandleUtils.getLong(FTSizeMetrics.Y_SCALE, size_metrics));
 *          }</pre>
 * <p>
 * Note that due to glyph hinting and the selected rendering mode these values are usually not exact;
 * consequently, they must be treated as unreliable with an error margin of at least one pixel!<br/>
 * Indeed, the only way to get the exact metrics is to render <i>all</i> glyphs. As this would be a definite
 * performance hit, it is up to client applications to perform such computations.<br/>
 * The {@link FTSizeMetrics} structure is valid for bitmap fonts also.<br/>
 *
 * <h3>TrueType fonts with native bytecode hinting</h3>
 * All applications that handle TrueType fonts with native hinting must be aware that TTFs expect different
 * rounding of vertical font dimensions. The application has to cater for this, especially if it wants to
 * rely on a TTF's vertical data (for example, to properly align box characters vertically).<br/>
 * Only the application knows in advance that it is going to use native hinting for TTFs! FreeType,
 * on the other hand, selects the hinting mode not at the time of creating an {@link FTSize} object
 * but much later, namely while calling {@link FreeType#FTLoadGlyph}.<br/>
 * Here is some pseudocode that illustrates a possible solution.
 *
 * <pre>{@code
 *                if (!FTGetFontFormat(face).getUtf8String(0).equals("TrueType")) &&
 *                          do_native_bytecode_hinting) {
 *                      ascender  = Math.round(FTMulFix(VarHandleUtils.getShort(FTFace.ASCENDER, face),
 *                                                      VarHandleUtils.getLong(FTSizeMetrics.Y_SCALE, size_metrics))));
 *                      descender = Math.round(FTMulFix(VarHandleUtils.getShort(FTFace.ASCENDER, face),
 *                                                      VarHandleUtils.getLong(FTSizeMetrics.Y_SCALE, size_metrics))));
 *                } else {
 *                      ascender  = VarHandleUtils.getLong(FTSizeMetrics.ASCENDER, size_metrics);
 *                      descender = VarHandleUtils.getLong(FTSizeMetrics.DESCENDER, size_metrics);
 *                }
 *
 *                height      = VarHandleUtils.getLong(FTSizeMetrics.HEIGHT, size_metrics));
 *                max_advance = VarHandleUtils.getLong(FTSizeMetrics.MAX_ADVANCE, size_metrics));
 *          }</pre>
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct  FT_Size_Metrics_
 *   {
 *     FT_UShort  x_ppem;  // horizontal pixels per EM
 *     FT_UShort y_ppem;   // vertical pixels per EM
 *
 *     FT_Fixed x_scale;   // scaling values used to convert font
 *     FT_Fixed y_scale;   // units to 26.6 fractional pixels
 *
 *     FT_Pos ascender;    // ascender in 26.6 frac. pixels
 *     FT_Pos descender;   // descender in 26.6 frac. pixels
 *     FT_Pos height;      // text height in 26.6 frac. pixels
 *     FT_Pos max_advance; // max horizontal advance, in 26.6
 *
 *   } FT_Size_Metrics;
 * }</pre>
 */
public final class FTSizeMetrics {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The width of the scaled EM square in pixels, hence the term ‘ppem’ (pixels per EM).
     * It is also referred to as ‘nominal width’.
     */
    public static final VarHandle X_PPEM;

    /**
     * The height of the scaled EM square in pixels, hence the term ‘ppem’ (pixels per EM).
     * It is also referred to as ‘nominal height’.
     */
    public static final VarHandle Y_PPEM;

    /**
     * A 16.16 fractional scaling value to convert horizontal metrics from font units to 26.6 fractional pixels.
     * Only relevant for scalable font formats.
     */
    public static final VarHandle X_SCALE;

    /**
     * A 16.16 fractional scaling value to convert vertical metrics from font units to 26.6 fractional pixels.
     * Only relevant for scalable font formats.
     */
    public static final VarHandle Y_SCALE;

    /**
     * The ascender in 26.6 fractional pixels, rounded up to an integer value. See {@link FTFace} for the details.
     */
    public static final VarHandle ASCENDER;

    /**
     * The descender in 26.6 fractional pixels, rounded down to an integer value. See {@link FTFace} for the details.
     */
    public static final VarHandle DESCENDER;

    /**
     * The height in 26.6 fractional pixels, rounded to an integer value. See {@link FTFace} for the details.
     */
    public static final VarHandle HEIGHT;

    /**
     * The maximum advance width in 26.6 fractional pixels, rounded to an integer value. See {@link FTFace} for the details.
     */
    public static final VarHandle MAX_ADVANCE;

    static {
        LayoutBuilder builder = new LayoutBuilder("SSLLLLLL", new String[]{
                "x_ppem", "y_ppem", "x_scale", "y_scale", "ascender", "descender", "height", "max_advance"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        X_PPEM = builder.varHandle("x_ppem");
        Y_PPEM = builder.varHandle("y_ppem");
        X_SCALE = builder.varHandle("x_scale");
        Y_SCALE = builder.varHandle("y_scale");
        ASCENDER = builder.varHandle("ascender");
        DESCENDER = builder.varHandle("descender");
        HEIGHT = builder.varHandle("height");
        MAX_ADVANCE = builder.varHandle("max_advance");
    }
}
