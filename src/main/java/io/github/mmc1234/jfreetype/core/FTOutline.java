package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.StructLayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

/**
 * This structure is used to describe an outline to the scan-line converter.
 *
 * @apiNote The B/W rasterizer only checks bit 2 in the tags array for the first point of each contour.
 * The drop-out mode as given with {@link #FT_OUTLINE_IGNORE_DROPOUTS}, {@link #FT_OUTLINE_SMART_DROPOUTS},
 * and {@link #FT_OUTLINE_INCLUDE_STUBS} in flags is then overridden.
 *
 * @implNote In freetype/ftimage.h
 * <pre>{@code
 *   typedef struct FT_Outline_
 *   {
 *     short       n_contours;      // number of contours in glyph
 *     short       n_points;        // number of points in the glyph
 *
 *     FT_Vector*  points;          // the outline's points
 *     char*       tags;            // the points flags
 *     short*      contours;        // the contour end points
 *
 *     int         flags;           // outline masks
 *     } FT_Outline;
 * }</pre>
 */
public final class FTOutline {

    /**
     * Value 0 is reserved.
     */
    public static final int FT_OUTLINE_NONE = 0x0;

    /**
     * If set, this flag indicates that the outline's field arrays (i.e., points, flags, and contours)
     * are ‘owned’ by the outline object, and should thus be freed when it is destroyed.
     */
    public static final int FT_OUTLINE_OWNER = 0x1;

    /**
     * By default, outlines are filled using the non-zero winding rule. If set to 1, the outline will
     * be filled using the even-odd fill rule (only works with the smooth rasterizer).
     */
    public static final int FT_OUTLINE_EVEN_ODD_FILL = 0x2;

    /**
     * By default, outside contours of an outline are oriented in clock-wise direction, as defined
     * in the TrueType specification. This flag is set if the outline uses the opposite direction
     * (typically for Type 1 fonts). This flag is ignored by the scan converter.
     */
    public static final int FT_OUTLINE_REVERSE_FILL = 0x4;

    /**
     * By default, the scan converter will try to detect drop-outs in an outline and correct the glyph bitmap
     * to ensure consistent shape continuity.
     * If set, this flag hints the scan-line converter to ignore such cases. See below for more information.
     */
    public static final int FT_OUTLINE_IGNORE_DROPOUTS = 0x8;

    /**
     * Select smart dropout control. If unset, use simple dropout control.
     * Ignored if {@link #FT_OUTLINE_IGNORE_DROPOUTS} is set. See below for more information.
     */
    public static final int FT_OUTLINE_SMART_DROPOUTS = 0x10;

    /**
     * If set, turn pixels on for ‘stubs’, otherwise exclude them.
     * Ignored if {@link #FT_OUTLINE_IGNORE_DROPOUTS} is set. See below for more information.
     */
    public static final int FT_OUTLINE_INCLUDE_STUBS = 0x20;

    /**
     * This flag indicates that this outline contains overlapping contours and the anti-aliased renderer
     * should perform oversampling to mitigate possible artifacts. This flag should not be set for
     * well-designed glyphs without overlaps because it quadruples the rendering time.
     */
    public static final int FT_OUTLINE_OVERLAP = 0x40;

    /**
     * This flag indicates that the scan-line converter should try to convert this outline to bitmaps
     * with the highest possible quality. It is typically set for small character sizes. Note that this is
     * only a hint that might be completely ignored by a given scan-converter.
     */
    public static final int FT_OUTLINE_HIGH_PRECISION = 0x100;

    /**
     * This flag is set to force a given scan-converter to only use a single pass over the outline to render
     * a bitmap glyph image. Normally, it is set for very large character sizes. It is only a hint that might be
     * completely ignored by a given scan-converter.
     */
    public static final int FT_OUTLINE_SINGLE_PASS = 0x200;

    // --- FT_OUTLINE_XXX Note:
    // The flags FT_OUTLINE_IGNORE_DROPOUTS, FT_OUTLINE_SMART_DROPOUTS, and FT_OUTLINE_INCLUDE_STUBS
    // are ignored by the smooth rasterizer.
    // There exists a second mechanism to pass the drop-out mode to the B/W rasterizer; see the tags field in FT_Outline.
    // Please refer to the description of the ‘SCANTYPE’ instruction in the OpenType specification
    // (in file ttinst1.doc) how simple drop-outs, smart drop-outs, and stubs are defined.
    // --- Note End

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    static {
        StructLayoutBuilder builder = new StructLayoutBuilder("SSAAAI", new String[] {
                "n_contours", "n_points", "points", "tags", "contours", "flags"
        });
        STRUCT_LAYOUT = builder.getStructLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();

    }
}
