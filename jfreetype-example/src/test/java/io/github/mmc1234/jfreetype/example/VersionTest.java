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
import io.github.mmc1234.jfreetype.core.FreeTypeLibrary;
import io.github.mmc1234.jfreetype.util.Scope;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemorySegment;
import org.testng.Assert;
import org.testng.annotations.Test;

public class VersionTest {

    private static String getVersionString(MemorySegment libPtr) {
        try (Scope scope = Scope.pushScope()) {
            MemorySegment outV1 = scope.newInt();
            MemorySegment outV2 = scope.newInt();
            MemorySegment outV3 = scope.newInt();
            FreeTypeLibrary.FTLibraryVersion(VarUtils.starAddress(libPtr), outV1, outV2, outV3);
            var v1 = VarUtils.getInt(outV1);
            var v2 = VarUtils.getInt(outV2);
            var v3 = VarUtils.getInt(outV3);
            return v1 + "." + v2 + "." + v3;
        }
    }

    @Test
    public void main() {
        FreeType.load();
        MemorySegment libPtr = VarUtils.newAddress();

        // Init
        var error = FreeTypeLibrary.FTInitFreeType(libPtr);
        Assert.assertEquals(FreeType.OK, error);

        // Print version
        var version = getVersionString(libPtr);
        System.out.println("Free Type Version = " + version);

        // Done
        FreeTypeLibrary.FTDoneFreeType(VarUtils.starAddress(libPtr));
    }
}
