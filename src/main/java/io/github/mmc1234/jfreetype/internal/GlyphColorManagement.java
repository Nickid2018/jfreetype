package io.github.mmc1234.jfreetype.internal;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;

import java.lang.invoke.MethodHandle;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.load;
import static io.github.mmc1234.jfreetype.util.FunctionDescriptorUtils.of;

public class GlyphColorManagement {
    public static MethodHandle FT_PALETTE_DATA_GET;
    public static MethodHandle FT_PALETTE_SELECT;
    public static MethodHandle FT_PALETTE_SET_FOREGROUND_COLOR;

    static void loadMethodHandles() {
        FT_PALETTE_DATA_GET = load("FT_Palette_Data_Get", of("IAA"));
        FT_PALETTE_SELECT = load("FT_Palette_Select", of("IASA"));
        // FT_PALETTE_SET_FOREGROUND_COLOR = load("FT_Palette_Set_Foreground_Color", of("IA1"));
        // TODO direct struct
    }
}
