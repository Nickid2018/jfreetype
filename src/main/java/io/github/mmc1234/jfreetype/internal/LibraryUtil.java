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

    protected static boolean isLoaded() {
        return loaded;
    }

    public static NativeSymbol getNativeSymbol(String name) {
        return SYMBOL_LOOKUP.lookup(name).orElseThrow(() -> new UnsatisfiedLinkError("Cannot find symbol " + name));
    }

    public static Optional<NativeSymbol> getNativeSymbolSilent(String name) {
        return SYMBOL_LOOKUP.lookup(name);
    }

    public static MethodHandle load(String name, FunctionDescriptor fd) {
        return LINKER.downcallHandle(getNativeSymbol(name), fd);
    }

    public static MethodHandle loadSilent(String name, FunctionDescriptor fd) {
        Optional<NativeSymbol> symbol =  getNativeSymbolSilent(name);
        return symbol.map(nativeSymbol -> LINKER.downcallHandle(nativeSymbol, fd)).orElse(null);
    }

    public static RuntimeException rethrow(Throwable e) {
        return new RuntimeException("An error occurred in invoking C-Library function!", e);
    }
}
