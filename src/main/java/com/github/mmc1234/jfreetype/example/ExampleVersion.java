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
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.ValueLayout;

public class ExampleVersion {
    private static int[] getVersion(MemorySegment lib) {
        try(var scope = ResourceScope.newConfinedScope()) {
            MemorySegment versionA = MemorySegment.allocateNative(ValueLayout.JAVA_INT.byteSize(), scope);
            MemorySegment versionB = MemorySegment.allocateNative(ValueLayout.JAVA_INT.byteSize(), scope);
            MemorySegment versionC = MemorySegment.allocateNative(ValueLayout.JAVA_INT.byteSize(), scope);

            FreeType.FTLibraryVersion(FreeType.deRef(lib), versionA, versionB, versionC);
            var v1 = versionA.getAtIndex(ValueLayout.JAVA_INT, 0);
            var v2 = versionB.getAtIndex(ValueLayout.JAVA_INT, 0);
            var v3 = versionC.getAtIndex(ValueLayout.JAVA_INT, 0);
            return new int[] {v1,v2,v3};
        }
    }

    public static void main(String[] args) {
        MemorySegment alib = MemorySegment.allocateNative(ValueLayout.ADDRESS, ResourceScope.globalScope());
        var error = FreeType.FTInitFreeType(alib);
        if(error != 0) throw new IllegalStateException("Fail init FreeType");
        var version = getVersion(alib);
        System.out.printf("Free Type Version=%d,%d,%d\n", version[0],version[1],version[2]);
        FreeType.FTDoneFreeType(FreeType.deRef(alib));
    }
}
