package io.github.mmc1234.jfreetype.internal;

import java.lang.invoke.MethodHandle;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.load;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.of;

public class SizeManagement {
    public static MethodHandle FT_NEW_SIZE;
    public static MethodHandle FT_DONE_SIZE;
    public static MethodHandle FT_ACTIVATE_SIZE;

    static void loadMethodHandles() {
        FT_NEW_SIZE = load("FT_New_Size", of("IAA"));
        FT_DONE_SIZE = load("FT_Done_Size", of("IA"));
        FT_ACTIVATE_SIZE = load("FT_Done_Size", of("IA"));
    }
}
