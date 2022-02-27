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
import io.github.mmc1234.jfreetype.core.FreeTypeFace;
import io.github.mmc1234.jfreetype.core.FreeTypeLibrary;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.*;

public class ExampleFace {

    public static void main(String[] args) {
        String fontName = "C:\\Windows\\Fonts\\Arial.ttf";
        MemorySegment libPtr = VarUtils.newAddress();
        MemorySegment facePtr = VarUtils.newAddress();
        MemorySegment filePath = VarUtils.newString(fontName);

        // Init
        var error = FreeTypeLibrary.FTInitFreeType(libPtr);
        Asserts.assertEquals(FreeType.OK, error);

        error = FreeTypeFace.FTNewFace(VarUtils.starAddress(libPtr), filePath.address(), 0, facePtr);
        Asserts.assertEquals(FreeType.OK, error);

        error = FreeTypeFace.FTSetCharSize(VarUtils.starAddress(facePtr), 0, 16 * 64, 300, 300);
        Asserts.assertEquals(FreeType.OK, error);

        // Print debug info
        System.out.println(FTFace.STRUCT_LAYOUT.byteSize());
        MemorySegment face = VarUtils.star(facePtr, FTFace.STRUCT_LAYOUT);
        System.out.println(VarUtils.getString(FTFace.FAMILY_NAME, face));
        System.out.println(VarUtils.getString(FTFace.STYLE_NAME, face));
        System.out.println(VarUtils.getInt(FTFace.ASCENDER, face));
        System.out.println(VarUtils.getInt(FTFace.DESCENDER, face));
        System.out.println(VarUtils.getInt(FTFace.HEIGHT, face));
        System.out.println(VarUtils.getInt(FTFace.UNITS_PER_EM, face));

        // Done
        FreeTypeFace.FTDoneFace(VarUtils.starAddress(facePtr));
        FreeTypeLibrary.FTDoneFreeType(VarUtils.starAddress(libPtr));
    }
}