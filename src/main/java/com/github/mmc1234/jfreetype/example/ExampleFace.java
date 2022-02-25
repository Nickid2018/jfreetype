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

package com.github.mmc1234.jfreetype.example;

import com.github.mmc1234.jfreetype.core.FreeType;
import jdk.incubator.foreign.*;

public class ExampleFace {
    public static void main(String[] args) {
        String str = "C:\\Windows\\Fonts\\Arial.ttf";
        MemorySegment alib = MemorySegment.allocateNative(ValueLayout.ADDRESS, ResourceScope.globalScope());
        MemorySegment aface = MemorySegment.allocateNative(ValueLayout.ADDRESS, ResourceScope.globalScope());
        MemorySegment path = MemorySegment.allocateNative(ValueLayout.ADDRESS.byteSize()*1024, ResourceScope.globalScope());
        path.setUtf8String(0, str);
        var error = FreeType.FTInitFreeType(alib);
        if(error != 0) throw new IllegalStateException("Fail init FreeType");

        error = FreeType.FTNewFace(FreeType.deRef(alib), path.address(), 0, aface);
        if(0 == error) {
            System.out.println("Success new face");
        } else {
            System.out.println("Fail new face(Error "+error+")");
        }
        FreeType.FTDoneFreeType(FreeType.deRef(alib));
    }
}
