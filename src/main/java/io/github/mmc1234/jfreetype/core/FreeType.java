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

package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.internal.*;

/**
 * A class indexes to all Core APIs.<br/>
 * Please use sub-interfaces to call functions.
 */
public class FreeType implements
        FTLoadFlags, FTErrors, FreeTypeLibrary, FreeTypeFace, FreeTypeSize, FreeTypeGlyph {

    /**
     * Load Method Handles of FreeType API.
     */
    public static boolean load() {
         return FreeTypeInternal.loadAll();
    }
}
