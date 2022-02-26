/*
 * Copyright 2022. mmc1234
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FTFace;
import io.github.mmc1234.jfreetype.core.FreeType;
import io.github.mmc1234.jfreetype.util.VarHandleUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.ValueLayout;

public class ExampleFace {

    public static void main(String[] args) {
        String str = "C:\\Windows\\Fonts\\Arial.ttf";
        MemorySegment alib = MemorySegment.allocateNative(ValueLayout.ADDRESS, ResourceScope.globalScope());
        MemorySegment aface = MemorySegment.allocateNative(ValueLayout.ADDRESS, ResourceScope.globalScope());
        MemorySegment path = MemorySegment.allocateNative(ValueLayout.ADDRESS.byteSize() * 1024, ResourceScope.globalScope());
        path.setUtf8String(0, str);
        var error = FreeType.FTInitFreeType(alib);
        if (error != 0)
            throw new IllegalStateException("Fail init FreeType");
        error = FreeType.FTNewFace(FreeType.deRef(alib), path.address(), 0, aface);
        if (error != 0)
            throw new IllegalStateException("Fail create face");
        error = FreeType.FTSetCharSize(FreeType.deRef(aface), 0, 16 * 64, 300, 300);
        if (error != 0)
            throw new IllegalStateException("Fail set char size");
        System.out.println(VarHandleUtils.getLong(FTFace.NUM_CHARMAPS,
                MemorySegment.ofAddress(FreeType.deRef(aface), FTFace.STRUCT_LAYOUT.byteSize(), ResourceScope.globalScope())));
        FreeType.FTDoneFace(FreeType.deRef(aface));
        FreeType.FTDoneFreeType(FreeType.deRef(alib));
    }
}
