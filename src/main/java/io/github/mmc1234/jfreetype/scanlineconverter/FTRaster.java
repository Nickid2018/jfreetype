package io.github.mmc1234.jfreetype.scanlineconverter;

/**
 * An opaque handle (pointer) to a raster object. Each object can be used independently
 * to convert an outline into a bitmap or pixmap.
 *
 * @apiNote In FreeType 2, all rasters are now encapsulated within specific {@code FTRenderer} modules
 * and only used in their context.
 * @implNote In freetype/ftimage.h
 * <pre>{@code
 *   typedef struct FT_RasterRec_*  FT_Raster;
 * }</pre>
 */
public final class FTRaster {

    // Hidden struct
}
