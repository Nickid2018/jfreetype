package io.github.mmc1234.jfreetype.glyph;

import io.github.mmc1234.jfreetype.image.FTBitmap;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

/**
 * A structure used for bitmap glyph images. This really is a ‘sub-class’ of {@link FTGlyph}.
 *
 * @apiNote You can typecast an {@link FTGlyph} to FTBitmapGlyph if you have glyph->format == FT_GLYPH_FORMAT_BITMAP.
 * This lets you access the bitmap's contents easily.<br/>
 * The corresponding pixel buffer is always owned by FTBitmapGlyph and is thus created and destroyed with it.
 *
 * @implNote In freetype/ftglyph.h
 * <pre>{@code
 *   typedef struct FT_BitmapGlyphRec_
 *   {
 *     FT_GlyphRec  root;
 *     FT_Int       left;
 *     FT_Int       top;
 *     FT_Bitmap    bitmap;
 *   } FT_BitmapGlyphRec;
 *     typedef struct FT_BitmapGlyphRec_* FT_BitmapGlyph;
 * }</pre>
 */
public final class FTBitmapGlyph {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The root {@link FTGlyph} fields.
     */
    public static final MethodHandle ROOT;

    /**
     * The left-side bearing, i.e., the horizontal distance from the current pen position to the left border of the glyph bitmap.
     */
    public static final VarHandle LEFT;

    /**
     * The top-side bearing, i.e., the vertical distance from the current pen position to the top border of the glyph bitmap.
     * This distance is positive for upwards y!
     */
    public static final VarHandle TOP;

    /**
     * A descriptor for the bitmap.
     */
    public static final MethodHandle BITMAP;

    static {
        LayoutBuilder builder = new LayoutBuilder("0II1", new String[] {
                "root", "left", "top", "bitmap"
        }, FTGlyph.STRUCT_LAYOUT, FTBitmap.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        ROOT = builder.structField("root");
        LEFT = builder.primitiveField("left");
        TOP = builder.primitiveField("top");
        BITMAP = builder.structField("bitmap");
    }
}
