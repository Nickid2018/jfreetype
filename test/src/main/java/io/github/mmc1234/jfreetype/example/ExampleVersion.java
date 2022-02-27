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

import io.github.mmc1234.jfreetype.core.FreeType;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.ValueLayout;

public class ExampleVersion {

    private static String getVersionString(MemorySegment libPtr) {
        try (var scope = ResourceScope.newConfinedScope()) {
            MemorySegment outV1 = MemorySegment.allocateNative(ValueLayout.JAVA_INT, scope);
            MemorySegment outV2 = MemorySegment.allocateNative(ValueLayout.JAVA_INT, scope);
            MemorySegment outV3 = MemorySegment.allocateNative(ValueLayout.JAVA_INT, scope);
            FreeType.FTLibraryVersion(VarUtils.starAddress(libPtr), outV1, outV2, outV3);
            var v1 = outV1.getAtIndex(ValueLayout.JAVA_INT, 0);
            var v2 = outV2.getAtIndex(ValueLayout.JAVA_INT, 0);
            var v3 = outV3.getAtIndex(ValueLayout.JAVA_INT, 0);
            return v1+"."+v2+"."+v3;
        }
    }

    public static void main(String[] args) {
        MemorySegment libPtr = MemorySegment.allocateNative(ValueLayout.ADDRESS, ResourceScope.globalScope());

        // Init
        var error = FreeType.FTInitFreeType(libPtr);
        Asserts.assertEquals(FreeType.OK, error);

        // Print version
        var version = getVersionString(libPtr);
        System.out.println("Free Type Version="+version);

        // Done
        FreeType.FTDoneFreeType(VarUtils.starAddress(libPtr));
    }
}
