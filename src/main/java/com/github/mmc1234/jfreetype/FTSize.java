package com.github.mmc1234.jfreetype;

import com.github.mmc1234.jfreetype.util.StructLayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * A handle to an object that models a face scaled to a given character size.
 *
 * @apiNote An {@link FTFace} has one active {@link FTSize} object that is used
 *          by functions like {@link FreeType#FTLoadGlyph} to determine
 *          the scaling transformation that in turn is used to load and hint glyphs and metrics.<br/>
 *          You can use {@link FreeType#FTSetCharSize}, {@link FreeType#FTSetPixelSizes},
 *          {@link FreeType#FTRequestSize} or even {@link FreeType#FTSelectSize} to change
 *          the content (i.e., the scaling values) of the active {@link FTSize}.<br/>
 *          You can use FTNewSize to create additional size objects for a given {@link FTFace},
 *          but they won't be used by other functions until you activate it through FTActivateSize.
 *          Only one size can be activated at any given time per face.
 */
public final class FTSize {


}
