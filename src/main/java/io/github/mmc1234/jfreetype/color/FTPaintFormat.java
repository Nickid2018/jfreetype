package io.github.mmc1234.jfreetype.color;

import io.github.mmc1234.jfreetype.CEnum;
import io.github.mmc1234.jfreetype.core.FTFace;

/**
 * Enumeration describing the different paint format types of the v1 extensions to the ‘COLR’ table,
 * see ‘https://github.com/googlefonts/colr-gradients-spec’.<br/>
 * The enumeration values losely correspond with the format numbers of the specification:
 * FreeType always returns a fully specified ‘Paint’ structure for the ‘Transform’, ‘Translate’,
 * ‘Scale’, ‘Rotate’, and ‘Skew’ table types even though the specification has different formats depending
 * on whether or not a center is specified, whether the scale is uniform in x and y direction or not, etc.
 * Also, only non-variable format identifiers are listed in this enumeration; as soon as support for variable
 * ‘COLR’ v1 fonts is implemented, interpolation is performed dependent on axis coordinates, which are configured
 * on the {@link FTFace} through FT_Set_Var_Design_Coordinates. This implies that always static, readily interpolated
 * values are returned in the ‘Paint’ structures.
 *
 * @implNote In freetype/ftcolor.h
 * <pre>{@code
 *   typedef enum  FT_PaintFormat_
 *   {
 *     FT_COLR_PAINTFORMAT_COLR_LAYERS     = 1,
 *     FT_COLR_PAINTFORMAT_SOLID           = 2,
 *     FT_COLR_PAINTFORMAT_LINEAR_GRADIENT = 4,
 *     FT_COLR_PAINTFORMAT_RADIAL_GRADIENT = 6,
 *     FT_COLR_PAINTFORMAT_SWEEP_GRADIENT  = 8,
 *     FT_COLR_PAINTFORMAT_GLYPH           = 10,
 *     FT_COLR_PAINTFORMAT_COLR_GLYPH      = 11,
 *     FT_COLR_PAINTFORMAT_TRANSFORM       = 12,
 *     FT_COLR_PAINTFORMAT_TRANSLATE       = 14,
 *     FT_COLR_PAINTFORMAT_SCALE           = 16,
 *     FT_COLR_PAINTFORMAT_ROTATE          = 24,
 *     FT_COLR_PAINTFORMAT_SKEW            = 28,
 *     FT_COLR_PAINTFORMAT_COMPOSITE       = 32,
 *     FT_COLR_PAINT_FORMAT_MAX            = 33,
 *     FT_COLR_PAINTFORMAT_UNSUPPORTED     = 255
 *   } FT_PaintFormat;
 * }</pre>
 */
public enum FTPaintFormat implements CEnum {

    FT_COLR_PAINTFORMAT_COLR_LAYERS(1),
    FT_COLR_PAINTFORMAT_SOLID(2),
    FT_COLR_PAINTFORMAT_LINEAR_GRADIENT(4),
    FT_COLR_PAINTFORMAT_RADIAL_GRADIENT(6),
    FT_COLR_PAINTFORMAT_SWEEP_GRADIENT(8),
    FT_COLR_PAINTFORMAT_GLYPH(10),
    FT_COLR_PAINTFORMAT_COLR_GLYPH(11),
    FT_COLR_PAINTFORMAT_TRANSFORM(12),
    FT_COLR_PAINTFORMAT_TRANSLATE(14),
    FT_COLR_PAINTFORMAT_SCALE(16),
    FT_COLR_PAINTFORMAT_ROTATE(24),
    FT_COLR_PAINTFORMAT_SKEW(28),
    FT_COLR_PAINTFORMAT_COMPOSITE(32),
    FT_COLR_PAINT_FORMAT_MAX(33),
    FT_COLR_PAINTFORMAT_UNSUPPORTED(255);

    private final int value;

    FTPaintFormat(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return value;
    }
}
