package com.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.VarHandle;

/**
 * Utility class for {@link VarHandle} operations.
 */
public class VarHandleUtils {

    public static void set(VarHandle handle, MemorySegment segment, int index, Object value) {
        handle.set(segment, index, value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(VarHandle handle, MemorySegment segment, int index) {
        return (T) handle.get(segment, index);
    }

    public static int getInt(VarHandle handle, MemorySegment segment, int index) {
        return (int) handle.get(segment, index);
    }

    public static short getShort(VarHandle handle, MemorySegment segment, int index) {
        return (short) handle.get(segment, index);
    }

    public static long getLong(VarHandle handle, MemorySegment segment, int index) {
        return (long) handle.get(segment, index);
    }

    public static byte getByte(VarHandle handle, MemorySegment segment, int index) {
        return (byte) handle.get(segment, index);
    }

    public static float getFloat(VarHandle handle, MemorySegment segment, int index) {
        return (float) handle.get(segment, index);
    }

    public static double getDouble(VarHandle handle, MemorySegment segment, int index) {
        return (double) handle.get(segment, index);
    }

    public static char getChar(VarHandle handle, MemorySegment segment, int index) {
        return (char) handle.get(segment, index);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(VarHandle handle, MemorySegment segment) {
        return (T) handle.get(segment, 0);
    }

    public static int getInt(VarHandle handle, MemorySegment segment) {
        return (int) handle.get(segment, 0);
    }

    public static short getShort(VarHandle handle, MemorySegment segment) {
        return (short) handle.get(segment, 0);
    }

    public static long getLong(VarHandle handle, MemorySegment segment) {
        return (long) handle.get(segment, 0);
    }

    public static byte getByte(VarHandle handle, MemorySegment segment) {
        return (byte) handle.get(segment, 0);
    }

    public static float getFloat(VarHandle handle, MemorySegment segment) {
        return (float) handle.get(segment, 0);
    }

    public static double getDouble(VarHandle handle, MemorySegment segment) {
        return (double) handle.get(segment, 0);
    }

    public static char getChar(VarHandle handle, MemorySegment segment) {
        return (char) handle.get(segment, 0);
    }
}
