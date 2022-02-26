package io.github.mmc1234.jfreetype.image;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * A structure used to describe a bitmap or pixmap to the raster.
 * Note that we now manage pixmaps of various depths through the pixel_mode field.
 *
 * @implNote In freetype/ftimage.h
 * <pre>{@code
 *   typedef struct FT_Bitmap_
 *   {
 *     unsigned int    rows;
 *     unsigned int    width;
 *     int             pitch;
 *     unsigned char*  buffer;
 *     unsigned short  num_grays;
 *     unsigned char   pixel_mode;
 *     unsigned char   palette_mode;
 *     void*           palette;
 *   } FT_Bitmap;
 * }</pre>
 */
public final class FTBitmap {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The number of bitmap rows.
     */
    public static final VarHandle ROWS;

    /**
     * The number of pixels in bitmap row.
     */
    public static final VarHandle WIDTH;

    /**
     * The pitch's absolute value is the number of bytes taken by one bitmap row, including padding.
     * However, the pitch is positive when the bitmap has a ‘down’ flow, and negative when it has an ‘up’ flow.
     * In all cases, the pitch is an offset to add to a bitmap pointer in order to go down one row.<br/>
     * Note that ‘padding’ means the alignment of a bitmap to a byte border, and FreeType functions normally
     * align to the smallest possible integer value.<br/>
     * For the B/W rasterizer, pitch is always an even number.<br/>
     * To change the pitch of a bitmap (say, to make it a multiple of 4), use {@code FT_Bitmap_Convert}.
     * Alternatively, you might use callback functions to directly render to the application's surface;
     * see the file example2.cpp in the tutorial for a demonstration.
     */
    public static final VarHandle PITCH;

    /**
     * A typeless pointer to the bitmap buffer. This value should be aligned on 32-bit boundaries in most cases.
     */
    public static final VarHandle BUFFER;

    /**
     * This field is only used with {@link FTPixelMode#FT_PIXEL_MODE_GRAY}; it gives the number of gray levels used in the bitmap.
     */
    public static final VarHandle NUM_GRAYS;

    /**
     * The pixel mode, i.e., how pixel bits are stored. See FT_Pixel_Mode for possible values.
     */
    public static final VarHandle PIXEL_MODE;

    /**
     * This field is intended for paletted pixel modes; it indicates how the palette is stored. Not used currently.
     */
    public static final VarHandle PALETTE_MODE;

    /**
     * A typeless pointer to the bitmap palette; this field is intended for paletted pixel modes. Not used currently.
     */
    public static final VarHandle PALETTE;

    static {
        LayoutBuilder builder = new LayoutBuilder("IIIASCCA", new String[] {
                "rows", "width", "pitch", "buffer", "num_grays", "pixel_mode", "palette_mode", "palette"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        ROWS = builder.varHandle("rows");
        WIDTH = builder.varHandle("rows");
        PITCH = builder.varHandle("pitch");
        BUFFER = builder.varHandle("buffer");
        NUM_GRAYS = builder.varHandle("num_grays");
        PIXEL_MODE = builder.varHandle("pixel_mode");
        PALETTE_MODE = builder.varHandle("palette_mode");
        PALETTE = builder.varHandle("palette");
    }
}
