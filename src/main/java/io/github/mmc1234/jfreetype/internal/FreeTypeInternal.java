package io.github.mmc1234.jfreetype.internal;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.loadNative;

public class FreeTypeInternal {

    public static boolean loadAll() {
        if(LibraryUtil.isLoaded()) return true;
        loadNative();
        VersionInternal.loadMethodHandles();
        BaseInterface.loadMethodHandles();
        UnicodeVariationSequences.loadMethodHandles();
        GlyphColorManagement.loadMethodHandles();
        GlyphLayerManagement.loadMethodHandles();
        GlyphManagement.loadMethodHandles();
        // TODO Mac Specific Interface
        SizeManagement.loadMethodHandles();

        return true;
    }

}
