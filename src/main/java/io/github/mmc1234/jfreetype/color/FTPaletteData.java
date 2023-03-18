package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.struct.AddressField;
import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import io.github.mmc1234.jfreetype.struct.ShortField;
import jdk.incubator.foreign.MemoryLayout;

/**
 * This structure holds the data of the ‘CPAL’ table.
 *
 * @apiNote Use function FT_Get_Sfnt_Name to map name IDs and entry name IDs to name strings.
 * Use function {@link io.github.mmc1234.jfreetype.core.FreeTypeGlyph#FTPaletteSelect}
 * to get the colors associated with a palette entry.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef struct FT_Palette_Data_ {
 *     FT_UShort         num_palettes;
 *     const FT_UShort*  palette_name_ids;
 *     const FT_UShort*  palette_flags;
 *
 *     FT_UShort         num_palette_entries;
 *     const FT_UShort*  palette_entry_name_ids;
 *   } FT_Palette_Data;
 * }</pre>
 */
public final class FTPaletteData {

    /**
     * The palette is appropriate to use when displaying the font on a light background such as white.
     */
    public static final short FT_PALETTE_FOR_LIGHT_BACKGROUND = 0x01;

    /**
     * The palette is appropriate to use when displaying the font on a dark background such as black.
     */
    public static final short FT_PALETTE_FOR_DARK_BACKGROUND  = 0x02;

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * The number of palettes.
     */
    public static final ShortField NUM_PALETTES;

    /**
     * An optional read-only array of palette name IDs with num_palettes elements,
     * corresponding to entries like ‘dark’ or ‘light’ in the font's ‘name’ table.<br/>
     * An empty name ID in the ‘CPAL’ table gets represented as value 0xFFFF.<br/>
     * NULL if the font's ‘CPAL’ table doesn't contain appropriate data.
     */
    public static final AddressField PALETTE_NAME_IDS;

    /**
     * An optional read-only array of palette flags with num_palettes elements.
     * Possible values are an ORed combination of {@link #FT_PALETTE_FOR_LIGHT_BACKGROUND}
     * and {@link #FT_PALETTE_FOR_DARK_BACKGROUND}.<br/>
     * NULL if the font's ‘CPAL’ table doesn't contain appropriate data.
     */
    public static final AddressField PALETTE_FLAGS;

    /**
     * The number of entries in a single palette. All palettes have the same size.
     */
    public static final ShortField NUM_PALETTE_ENTRIES;

    /**
     * An optional read-only array of palette entry name IDs with {@link #NUM_PALETTE_ENTRIES}.
     * In each palette, entries with the same index have the same function. For example,
     * index 0 might correspond to string ‘outline’ in the font's ‘name’ table to indicate
     * that this palette entry is used for outlines, index 1 might correspond to ‘fill’
     * to indicate the filling color palette entry, etc.<br/>
     * An empty entry name ID in the ‘CPAL’ table gets represented as value 0xFFFF.<br/>
     * NULL if the font's ‘CPAL’ table doesn't contain appropriate data.
     */
    public static final AddressField PALETTE_ENTRY_NAME_IDS;

    static {
        LayoutBuilder builder = new LayoutBuilder("SAASA", new String[] {
                "num_palettes", "palette_name_ids", "palette_flags", "num_palette_entries", "palette_entry_name_ids"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        NUM_PALETTES = builder.newShort("num_palettes");
        PALETTE_NAME_IDS = builder.newAddress("palette_name_ids");
        PALETTE_FLAGS = builder.newAddress("palette_flags");
        NUM_PALETTE_ENTRIES = builder.newShort("num_palette_entries");
        PALETTE_ENTRY_NAME_IDS = builder.newAddress("palette_entry_name_ids");
    }
}
