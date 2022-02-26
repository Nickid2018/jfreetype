package io.github.mmc1234.jfreetype.internal;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.NativeSymbol;
import jdk.incubator.foreign.SymbolLookup;

import java.lang.invoke.MethodHandle;

public class LibraryUtil {
    public static void loadNative() {
        String lib = System.getProperty("jfreetype.library");
        if (lib == null)
            System.loadLibrary("freetype");
        else
            System.load(lib);
    }

    public static NativeSymbol getNativeSymbol(String name) {
        return SymbolLookup.loaderLookup().lookup(name).get();
    }

    public static MethodHandle load(String name, FunctionDescriptor fd) {
        return CLinker.systemCLinker().downcallHandle(getNativeSymbol(name), fd);
    }
}
