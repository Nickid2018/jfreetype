package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for {@link VarHandle} operations.
 */
public class VarHandleUtils {

    public static VarHandle createAccess(MemoryLayout seqLayout, String path) {
        List<MemoryLayout.PathElement> elements = Arrays.stream(path.split("\\."))
                .map(MemoryLayout.PathElement::groupElement).collect(Collectors.toList());
        elements.add(0, MemoryLayout.PathElement.sequenceElement());
        return seqLayout.varHandle(elements.toArray(MemoryLayout.PathElement[]::new));
    }

    public static void set(VarHandle handle, MemorySegment segment, int index, Object value) {
        handle.set(segment, index, value);
    }

    public static MemorySegment getSegment(VarHandle handle, MemorySegment segment, int index,
                                           MemoryLayout layout, ResourceScope scope) {
        MemoryAddress addr = getAddress(handle, segment, index);
        return MemorySegment.ofAddress(addr, layout.byteSize(), scope);
    }

    public static MemoryAddress getAddress(VarHandle handle, MemorySegment segment, int index) {
        return (MemoryAddress) handle.get(segment, index);
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

    public static MemorySegment getSegment(VarHandle handle, MemorySegment segment, MemoryLayout layout, ResourceScope scope) {
        MemoryAddress addr = getAddress(handle, segment);
        return MemorySegment.ofAddress(addr, layout.byteSize(), scope);
    }

    public static MemoryAddress getAddress(VarHandle handle, MemorySegment segment) {
        return (MemoryAddress) handle.get(segment, 0);
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
