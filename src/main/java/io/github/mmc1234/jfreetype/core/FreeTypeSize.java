package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.In;
import io.github.mmc1234.jfreetype.Out;
import io.github.mmc1234.jfreetype.internal.SizeManagement;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import static io.github.mmc1234.jfreetype.internal.LibraryUtil.rethrow;

/**
 * An interface stores size operations.
 */
public interface FreeTypeSize {

    /**
     * Create a new size object from a given face object.
     *
     * @param face  A handle to a parent face object.
     * @param asize A handle to a new size object.
     * @return A handle to a new size object.
     * @apiNote You need to call {@link #FTActivateSize} in order to select the new size for upcoming calls to
     * {@link FreeTypeFace#FTSetPixelSizes}, {@link FreeTypeFace#FTSetCharSize}, {@link FreeTypeFace#FTLoadGlyph}
     * , {@link FreeTypeFace#FTLoadChar}, etc.
     */
    static int FTNewSize(@In MemoryAddress face, @Out MemorySegment asize) {
        try {
            return (int) SizeManagement.FT_NEW_SIZE.invoke(face, asize.address());
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Discard a given size object. Note that {@link FreeTypeFace#FTDoneFace} automatically discards all size objects allocated
     * with {@link #FTNewSize}.
     *
     * @param size A handle to a target size object.
     * @return FreeType error code. 0 means success.
     */
    static int FTDoneSize(@In MemoryAddress size) {
        try {
            return (int) SizeManagement.FT_DONE_SIZE.invoke(size);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }

    /**
     * Even though it is possible to create several size objects for a given face (see {@link #FTNewSize} for details),
     * functions like {@link FreeTypeFace#FTLoadGlyph} or {@link FreeTypeFace#FTLoadChar} only use the one that has been activated last to
     * determine the ‘current character pixel size’.<br/>
     * This function can be used to ‘activate’ a previously created size object.
     *
     * @param size A handle to a target size object.
     * @return FreeType error code. 0 means success.
     * @apiNote If face is the size's parent face object, this function changes the value of face->size to the input size handle.
     */
    static int FTActivateSize(@In MemoryAddress size) {
        try {
            return (int) SizeManagement.FT_ACTIVATE_SIZE.invoke(size);
        } catch (Throwable e) {
            throw rethrow(e);
        }
    }
}
