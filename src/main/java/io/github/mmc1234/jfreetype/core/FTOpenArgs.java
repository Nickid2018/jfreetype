package io.github.mmc1234.jfreetype.core;

import io.github.mmc1234.jfreetype.util.LayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * A structure to indicate how to open a new font file or stream. A pointer to such a structure
 * can be used as a parameter for the functions {@link FreeTypeFace#FTOpenFace} and {@link FreeTypeFace#FTAttachStream}.
 *
 * @apiNote The stream type is determined by the contents of {@link #FLAGS}:
 * <ul>
 *     <li>
 *         If the {@link #FT_OPEN_MEMORY} bit is set, assume that this is a memory file of memory_size bytes,
 *         located at memory_address. The data are not copied, and the client is responsible for releasing
 *         and destroying them after the corresponding call to {@link FreeTypeFace#FTDoneFace}.
 *     </li>
 *     <li>
 *         Otherwise, if the {@link #FT_OPEN_STREAM} bit is set, assume that a custom input stream stream is used.
 *     </li>
 *     <li>
 *         Otherwise, if the {@link #FT_OPEN_PATHNAME} bit is set, assume that this is a normal file and use pathname to open it.
 *     </li>
 *     <li>
 *         If none of the above bits are set or if multiple are set at the same time, the flags are invalid
 *         and {@link FreeTypeFace#FTOpenFace} fails.
 *     </li>
 *     <li>
 *         If the {@link #FT_OPEN_DRIVER} bit is set, {@link FreeTypeFace#FTOpenFace} only tries to open the file with the driver
 *         whose handler is in driver.
 *     </li>
 *     <li>
 *         If the {@link #FT_OPEN_PARAMS} bit is set, the parameters given by num_params and params is used. They are ignored otherwise.
 *     </li>
 * </ul>
 * Ideally, both the pathname and params fields should be tagged as ‘const’; this is missing for
 * API backward compatibility. In other words, applications should treat them as read-only.
 * @implNote In freetype/freetype.h
 * <pre>{@code
 *   typedef struct  FT_Open_Args_
 *   {
 *     FT_UInt         flags;
 *     const FT_Byte*  memory_base;
 *     FT_Long         memory_size;
 *     FT_String*      pathname;
 *     FT_Stream       stream;
 *     FT_Module       driver;
 *     FT_Int          num_params;
 *     FT_Parameter*   params;
 *   } FT_Open_Args;
 * }</pre>
 */
public final class FTOpenArgs {

    // --- A list of bit field constants used within the flags field of the FTOpenArgs structure.
    // The FT_OPEN_MEMORY, FT_OPEN_STREAM, and FT_OPEN_PATHNAME flags are mutually exclusive.

    /**
     * This is a memory-based stream.
     */
    public static final int FT_OPEN_MEMORY = 0x1;

    /**
     * Copy the stream from {@link #STREAM} .
     */
    public static final int FT_OPEN_STREAM = 0x2;

    /**
     * Create a new input stream from {@link #PATHNAME}.
     */
    public static final int FT_OPEN_PATHNAME = 0x4;

    /**
     * Use {@link #DRIVER}.
     */
    public static final int FT_OPEN_DRIVER = 0x8;

    /**
     * Use {@link #NUM_PARAMS} and {@link #PARAMS}.
     */
    public static final int FT_OPEN_PARAMS = 0x10;

    // --- Flags End

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * A set of bit flags indicating how to use the structure.
     */
    public static final VarHandle FLAGS;

    /**
     * The first byte of the file in memory.
     */
    public static final VarHandle MEMORY_BASE;

    /**
     * The size in bytes of the file in memory.
     */
    public static final VarHandle MEMORY_SIZE;

    /**
     * A pointer to an 8-bit file pathname, which must be a C string (i.e., no null bytes except at the very end).
     * The pointer is not owned by FreeType.
     */
    public static final VarHandle PATHNAME;

    /**
     * A handle to a source stream object.
     */
    public static final VarHandle STREAM;

    /**
     * This field is exclusively used by {@link FreeTypeFace#FTOpenFace}; it simply specifies the font driver to use for opening the face.
     * If set to NULL, FreeType tries to load the face with each one of the drivers in its list.
     */
    public static final VarHandle DRIVER;

    /**
     * The number of extra parameters.
     */
    public static final VarHandle NUM_PARAMS;

    /**
     * Extra parameters passed to the font driver when opening a new face.
     */
    public static final VarHandle PARAMS;

    static {
        LayoutBuilder builder = new LayoutBuilder("IAIAAAIA", new String[]{
                "flags", "memory_base", "memory_size", "pathname", "stream", "driver", "num_params", "params"
        });
        STRUCT_LAYOUT = builder.getGroupLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        FLAGS = builder.varHandle("flags");
        MEMORY_BASE = builder.varHandle("memory_base");
        MEMORY_SIZE = builder.varHandle("memory_size");
        PATHNAME = builder.varHandle("pathname");
        STREAM = builder.varHandle("stream");
        DRIVER = builder.varHandle("driver");
        NUM_PARAMS = builder.varHandle("num_params");
        PARAMS = builder.varHandle("params");
    }
}
