package io.github.mmc1234.jfreetype.easyfont;

import io.github.mmc1234.jfreetype.core.FTErrors;
import io.github.mmc1234.jfreetype.core.FTFace;
import io.github.mmc1234.jfreetype.core.FreeTypeFace;
import io.github.mmc1234.jfreetype.core.FreeTypeLibrary;
import io.github.mmc1234.jfreetype.util.Scope;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

public class EasyFontLibrary implements AutoCloseable {

    private final Scope resourceScope;
    private final MemoryAddress library;

    public EasyFontLibrary() {
        resourceScope = Scope.pushScope();
        MemorySegment ptrLibrary = resourceScope.newAddress();
        FTErrors.checkCode(FreeTypeLibrary.FTInitFreeType(ptrLibrary));
        library = VarUtils.starAddress(ptrLibrary);
    }

    public EasyFont createFont(String file, int faceIndex) {
        MemorySegment ptrFace = resourceScope.newAddress();
        MemoryAddress name = resourceScope.newString(file).address();
        FTErrors.checkCode(FreeTypeFace.FTNewFace(library, name, faceIndex, ptrFace));
        return new EasyFont(resourceScope.star(ptrFace, FTFace.STRUCT_LAYOUT), resourceScope);
    }

    public EasyFont createFontInMemory(MemoryAddress base, long length, int faceIndex) {
        MemorySegment ptrFace = resourceScope.newAddress();
        FTErrors.checkCode(FreeTypeFace.FTNewMemoryFace(library, base, length, faceIndex, ptrFace));
        return new EasyFont(resourceScope.star(ptrFace, FTFace.STRUCT_LAYOUT), resourceScope);
    }

    public EasyFont createFontInMemory(byte[] data, int faceIndex) {
        MemorySegment base = MemorySegment.ofArray(data);
        return createFontInMemory(base.address(), data.length, faceIndex);
    }

    @Override
    public void close() {
        FTErrors.checkCode(FreeTypeLibrary.FTDoneFreeType(library));
        resourceScope.close();
    }
}
