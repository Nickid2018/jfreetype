package io.github.mmc1234.jfreetype.internal;

import java.lang.invoke.MethodHandle;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.load;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.ofVoid;

public class VersionInternal {
    public static MethodHandle FT_LIBRARY_VERSION;

    static void loadMethodHandles() {
        FT_LIBRARY_VERSION = load("FT_Library_Version", ofVoid("AAAA"));
    }
}
