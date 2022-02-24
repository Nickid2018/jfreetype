package com.github.mmc1234.jfreetype;

/**
 * A handle to a character map (usually abbreviated to ‘charmap’).
 * A charmap is used to translate character codes in a given encoding into glyph indexes for
 * its parent's face. Some font formats may provide several charmaps per font.
 * <p>
 * Each face object owns zero or more charmaps,
 * but only one of them can be ‘active’,
 * providing the data used by {@link FreeType#FTGetCharIndex} or {@link FreeType#FTLoadChar}.
 * <p>
 * The list of available charmaps in a face is available through
 * the face->numcharmaps and face->charmaps fields of FTFaceRec.
 * <p>
 * The currently active charmap is available as face->charmap.
 * You should call {@link FreeType#FTSetCharmap} to change it.
 * <p>
 * Note:
 * <p>
 * When a new face is created (either through {@link FreeType#FTNewFace} or {@link FreeType#FTOpenFace}),
 * the library looks for a Unicode charmap within
 * the list and automatically activates it.
 * If there is no Unicode charmap, FreeType doesn't set an ‘active’ charmap.
 */
public interface FTCharMap {
}
