package io.github.mmc1234.jfreetype.core;

/**
 * A handle to a typographic face object.
 * A face object models a given typeface, in a given style.
 *
 * @apiNote A face object also owns a single {@link FTGlyphSlot} object,
 * as well as one or more {@link FTSize} objects.<br/>
 * Use {@link FreeType#FTNewFace} or {@link FreeType#FTOpenFace} to create
 * a new face object from a given filepath or a custom input stream.<br/>
 * Use {@link FreeType#FTDoneFace} to destroy it (along with its slot and sizes).<br/>
 * An {@link FTFace} object can only be safely used from one thread at a time.
 * Similarly, creation and destruction of {@link FTFace}
 * with the same {@link FTLibrary} object can only be done from one thread at a time.
 * On the other hand, functions like {@link FreeType#FTLoadGlyph}
 * and its siblings are thread-safe and do not need the lock to be held as long as
 * the same {@link FTFace} object is not used from multiple threads at the same time.
 */
public interface FTFace {

}
