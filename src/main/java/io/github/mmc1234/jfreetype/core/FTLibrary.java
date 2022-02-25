package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.scanlineconverter.FTRaster;
import io.github.mmc1234.jfreetype.system.FTMemory;

/**
 * A handle to a FreeType library instance.
 * Each ‘library’ is completely independent from the others;
 * it is the ‘root’ of a set of objects like fonts, faces, sizes, etc.<br/>
 * It also embeds a memory manager (see {@link FTMemory}),
 * as well as a scan-line converter object (see {@link FTRaster}).<br/>
 * [Since 2.5.6] In multi-threaded applications it is easiest to use one {@link FTLibrary} object per thread.
 * In case this is too cumbersome, a single {@link FTLibrary} object across threads is possible also
 * as long as a mutex lock is used around {@link FreeType#FTNewFace} and {@link FreeType#FTDoneFace}.
 *
 * @apiNote Library objects are normally created by {@link FreeType#FTInitFreeType}, and destroyed
 * with {@link FreeType#FTDoneFreeType}. If you need reference-counting (cf. {@code FT_Reference_Library}),
 * use {@code FT_New_Library} and {@code FT_Done_Library}.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct FT_LibraryRec_  *FT_Library;
 * }</pre>
 */
public final class FTLibrary {

    // Hidden struct
}
