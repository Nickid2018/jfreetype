package io.github.mmc1234.jfreetype.internal;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.NativeSymbol;
import jdk.incubator.foreign.SymbolLookup;

import java.lang.invoke.MethodHandle;
import java.util.Optional;

public class LibraryUtil {

    private static volatile boolean loaded = false;

    private static final SymbolLookup SYMBOL_LOOKUP = SymbolLookup.loaderLookup();
    private static final CLinker LINKER = CLinker.systemCLinker();

    public static void loadNative() {
        if (loaded)
            return;
        String lib = System.getProperty("jfreetype.library");
        if (lib == null)
            System.loadLibrary("freetype");
        else
            System.load(lib);
        loaded = true;
    }

    public static NativeSymbol getNativeSymbol(String name) {
        return SYMBOL_LOOKUP.lookup(name).orElseThrow(UnsatisfiedLinkError::new);
    }

    public static Optional<NativeSymbol> getNativeSymbolSilent(String name) {
        return SYMBOL_LOOKUP.lookup(name);
    }

    public static MethodHandle load(String name, FunctionDescriptor fd) {
        return LINKER.downcallHandle(getNativeSymbol(name), fd);
    }

    public static MethodHandle loadSilent(String name, FunctionDescriptor fd) {
        Optional<NativeSymbol> symbol =  getNativeSymbolSilent(name);
        if (symbol.isEmpty())
            return null;
        return LINKER.downcallHandle(symbol.get(), fd);
    }

    public static RuntimeException rethrow(Throwable e) {
        return new RuntimeException("An error occurred in invoking C-Library function!", e);
    }
}
