package io.github.mmc1234.jfreetype.image;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * This structure is used to describe an outline to the scan-line converter.
 *
 * @apiNote The B/W rasterizer only checks bit 2 in the tags array for the first point of each contour.
 * The drop-out mode as given with {@link #FT_OUTLINE_IGNORE_DROPOUTS}, {@link #FT_OUTLINE_SMART_DROPOUTS},
 * and {@link #FT_OUTLINE_INCLUDE_STUBS} in flags is then overridden.
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

    /**
     * The number of contours in the outline.
     */
    public static final VarHandle N_CONTOURS;

    /**
     * The number of points in the outline.
     */
    public static final VarHandle N_POINTS;

    /**
     * A pointer to an array of n_points {@link FTVector} elements, giving the outline's point coordinates.
     */
    public static final VarHandle POINTS;

    /**
     * A pointer to an array of n_points chars, giving each outline point's type.<br/>
     * If bit 0 is unset, the point is ‘off’ the curve, i.e., a Bezier control point, while it is ‘on’ if set.<br/>
     * Bit 1 is meaningful for ‘off’ points only. If set, it indicates a third-order Bezier arc control point;
     * and a second-order control point if unset.<br/>
     * If bit 2 is set, bits 5-7 contain the drop-out mode (as defined in the OpenType specification;
     * the value is the same as the argument to the ‘SCANMODE’ instruction).<br/>
     * Bits 3 and 4 are reserved for internal purposes.
     */
    public static final VarHandle TAGS;

    /**
     * An array of n_contours shorts, giving the end point of each contour within the outline. For example,
     * the first contour is defined by the points ‘0’ to contours[0], the second one is defined
     * by the points contours[0]+1 to contours[1], etc.
     */
    public static final VarHandle CONTOURS;

    /**
     * A set of bit flags used to characterize the outline and give hints to the scan-converter and hinter
     * on how to convert/grid-fit it. See FT_OUTLINE_XXX.
     */
    public static final VarHandle FLAGS;

    static {
        LayoutBuilder builder = new LayoutBuilder("SSAAAI", new String[]{
                "n_contours", "n_points", "points", "tags", "contours", "flags"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        N_CONTOURS = builder.varHandle("n_contours");
        N_POINTS = builder.varHandle("n_points");
        POINTS = builder.varHandle("points");
        TAGS = builder.varHandle("tags");
        CONTOURS = builder.varHandle("contours");
        FLAGS = builder.varHandle("flags");
    }
}
