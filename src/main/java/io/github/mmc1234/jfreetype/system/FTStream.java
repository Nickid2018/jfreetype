package io.github.mmc1234.jfreetype.system;

import io.github.mmc1234.jfreetype.util.StructLayoutBuilder;
import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

/**
 * A structure used to describe an input stream.
 *
 * @implNote In freetype/ftsystem.h
 * <pre>{@code
 *   typedef struct FT_StreamRec_
 *   {
 *     unsigned char*       base;
 *     unsigned long        size;
 *     unsigned long        pos;
 *
 *     FT_StreamDesc        descriptor;
 *     FT_StreamDesc        pathname;
 *     FT_Stream_IoFunc     read;
 *     FT_Stream_CloseFunc  close;
 *
 *     FT_Memory            memory;
 *     unsigned char*       cursor;
 *     unsigned char*       limit;
 *   } FT_StreamRec;
 * }</pre>
 */
public final class FTStream {

    public static final MemoryLayout STRUCT_LAYOUT;
    public static final MemoryLayout SEQUENCE_LAYOUT;

    /**
     * For memory-based streams, this is the address of the first stream byte in memory.
     * This field should always be set to NULL for disk-based streams.
     */
    public static final VarHandle BASE;

    /**
     * The stream size in bytes.<br/>
     * In case of compressed streams where the size is unknown before actually doing the decompression,
     * the value is set to 0x7FFFFFFF. (Note that this size value can occur for normal streams also;
     * it is thus just a hint.)
     */
    public static final VarHandle SIZE;

    /**
     * The current position within the stream.
     */
    public static final VarHandle POS;

    /**
     * This field is a union that can hold an integer or a pointer.
     * It is used by stream implementations to store file descriptors or FILE* pointers.
     */
    public static final VarHandle DESCRIPTOR = null;

    /**
     * This field is completely ignored by FreeType. However,
     * it is often useful during debugging to use it to store the stream's filename (where available).
     */
    public static final VarHandle PATHNAME = null;

    /**
     * The stream's input function.
     *
     * @implSpec Function: (FT_Stream stream, unsigned long offset, unsigned char* buffer, unsigned long count)<br/>
     * Input:
     * <ul>
     *     <li>stream - A handle to the source stream.</li>
     *     <li>offset - The offset of read in stream (always from start).</li>
     *     <li>buffer - The address of the read buffer.</li>
     *     <li>count - The number of bytes to read from the stream.</li>
     * </ul>
     * Return: The number of bytes effectively read by the stream.<br/>
     * Note: This function might be called to perform a seek or skip operation with a count of 0.
     * A non-zero return value then indicates an error.
     */
    public static final VarHandle READ;

    /**
     * The stream's close function.
     *
     * @implSpec Function: (FT_Stream stream)<br/>
     * Input:
     * <ul>
     *     <li>stream - A handle to the target stream.</li>
     * </ul>
     */
    public static final VarHandle CLOSE;

    /**
     * The memory manager to use to preload frames. This is set internally by FreeType and shouldn't be touched
     * by stream implementations.
     */
    public static final VarHandle MEMORY;

    /**
     * This field is set and used internally by FreeType when parsing frames.
     * In particular, the FT_GET_XXX macros use this instead of the pos field.
     */
    public static final VarHandle CURSOR;

    /**
     * This field is set and used internally by FreeType when parsing frames.
     */
    public static final VarHandle LIMIT;

    static {
        StructLayoutBuilder builder = new StructLayoutBuilder("ALL00AAAAA", new String[]{
                "base", "size", "pos", "descriptor", "pathname", "read", "close", "memory", "cursor", "limit"
        }, FTStreamDesc.STRUCT_LAYOUT);
        STRUCT_LAYOUT = builder.getStructLayout();
        SEQUENCE_LAYOUT = builder.getSequenceLayout();
        BASE = builder.varHandle("base");
        SIZE = builder.varHandle("size");
        POS = builder.varHandle("pos");
        READ = builder.varHandle("read");
        CLOSE = builder.varHandle("close");
        MEMORY = builder.varHandle("memory");
        CURSOR = builder.varHandle("cursor");
        LIMIT = builder.varHandle("limit");
    }
}
