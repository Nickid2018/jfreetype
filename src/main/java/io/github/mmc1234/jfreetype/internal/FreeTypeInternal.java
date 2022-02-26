package io.github.mmc1234.jfreetype.internal;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.loadNative;

public class FreeTypeInternal {

    public static final boolean loadAll() {
        loadNative();
        Version.loadMethodHandles();
        BaseInterface.loadMethodHandles();
        UnicodeVariationSequences.loadMethodHandles();
        // TODO Glyph Color Management
        // TODO Glyph Layer Management
        // TODO Glyph Management
        // TODO Mac Specific Interface
        SizeManagement.loadMethodHandles();

        return true;
    }

}
