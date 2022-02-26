package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static jdk.incubator.foreign.ValueLayout.*;

/**
 * Utility class for variable operations.
 */
public class VarUtils {

    /**
     * Create access to a field.
     * @param seqLayout sequence layout of the struct
     * @param path path to the element
     * @return a VarHandle to operate the field
     */
    public static VarHandle createAccess(MemoryLayout seqLayout, String path) {
        List<MemoryLayout.PathElement> elements = Arrays.stream(path.split("\\."))
                .map(MemoryLayout.PathElement::groupElement).collect(Collectors.toList());
        elements.add(0, MemoryLayout.PathElement.sequenceElement());
        return seqLayout.varHandle(elements.toArray(MemoryLayout.PathElement[]::new));
    }

    /**
     * Set value to the variable.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @param value value to set
     */
    public static void set(VarHandle handle, MemorySegment segment, int index, Object value) {
        handle.set(segment, index, value);
    }

    /**
     * Get segment from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @param layout layout of the struct
     * @param scope scope of the segment
     * @return segment of the field
     */
    public static MemorySegment getSegment(VarHandle handle, MemorySegment segment, int index,
                                           MemoryLayout layout, ResourceScope scope) {
        MemoryAddress addr = getAddress(handle, segment, index);
        return MemorySegment.ofAddress(addr, layout.byteSize(), scope);
    }

    /**
     * Get address from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return address in the field
     */
    public static MemoryAddress getAddress(VarHandle handle, MemorySegment segment, int index) {
        return (MemoryAddress) handle.get(segment, index);
    }

    /**
     * Get string from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return string in the field
     */
    public static String getString(VarHandle handle, MemorySegment segment, int index) {
        return getAddress(handle, segment, index).getUtf8String(0);
    }

    /**
     * Get integer from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return integer in the field
     */
    public static int getInt(VarHandle handle, MemorySegment segment, int index) {
        return (int) handle.get(segment, index);
    }

    /**
     * Get short from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return short in the field
     */
    public static short getShort(VarHandle handle, MemorySegment segment, int index) {
        return (short) handle.get(segment, index);
    }

    /**
     * Get long from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return long in the field
     */
    public static long getLong(VarHandle handle, MemorySegment segment, int index) {
        return (long) handle.get(segment, index);
    }

    /**
     * Get byte from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return byte in the field
     */
    public static byte getByte(VarHandle handle, MemorySegment segment, int index) {
        return (byte) handle.get(segment, index);
    }
    /**
     * Get float from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return float in the field
     */
    public static float getFloat(VarHandle handle, MemorySegment segment, int index) {
        return (float) handle.get(segment, index);
    }

    /**
     * Get double from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return double in the field
     */
    public static double getDouble(VarHandle handle, MemorySegment segment, int index) {
        return (double) handle.get(segment, index);
    }

    /**
     * Get char from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return char in the field
     */
    public static char getChar(VarHandle handle, MemorySegment segment, int index) {
        return (char) handle.get(segment, index);
    }

    /**
     * Get segment from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param layout layout of the struct
     * @param scope scope of the segment
     * @return segment of the field
     */
    public static MemorySegment getSegment(VarHandle handle, MemorySegment segment, MemoryLayout layout, ResourceScope scope) {
        MemoryAddress addr = getAddress(handle, segment);
        return MemorySegment.ofAddress(addr, layout.byteSize(), scope);
    }

    /**
     * Get address from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return address in the field
     */
    public static MemoryAddress getAddress(VarHandle handle, MemorySegment segment) {
        return (MemoryAddress) handle.get(segment, 0);
    }

    /**
     * Get string from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return string in the field
     */
    public static String getString(VarHandle handle, MemorySegment segment) {
        return getAddress(handle, segment).getUtf8String(0);
    }

    /**
     * Get integer from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return integer in the field
     */
    public static int getInt(VarHandle handle, MemorySegment segment) {
        return (int) handle.get(segment, 0);
    }

    /**
     * Get short from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return short in the field
     */
    public static short getShort(VarHandle handle, MemorySegment segment) {
        return (short) handle.get(segment, 0);
    }

    /**
     * Get long from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return long in the field
     */
    public static long getLong(VarHandle handle, MemorySegment segment) {
        return (long) handle.get(segment, 0);
    }

    /**
     * Get byte from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return byte in the field
     */
    public static byte getByte(VarHandle handle, MemorySegment segment) {
        return (byte) handle.get(segment, 0);
    }

    /**
     * Get float from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return float in the field
     */
    public static float getFloat(VarHandle handle, MemorySegment segment) {
        return (float) handle.get(segment, 0);
    }

    /**
     * Get double from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return double in the field
     */
    public static double getDouble(VarHandle handle, MemorySegment segment) {
        return (double) handle.get(segment, 0);
    }

    /**
     * Get char from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return char in the field
     */
    public static char getChar(VarHandle handle, MemorySegment segment) {
        return (char) handle.get(segment, 0);
    }

    /**
     * Dereference the address from a segment.
     * @param segment segment to operate
     * @return address in the segment
     */
    public static MemoryAddress starAddress(MemorySegment segment) {
        return segment.getAtIndex(ADDRESS, 0);
    }

    /**
     * Get the object that the segment is pointing to.
     * @param segment segment to operate
     * @param layout layout of the object
     * @param scope scope of the segment
     * @return object which is pointed
     */
    public static MemorySegment star(MemorySegment segment, MemoryLayout layout, ResourceScope scope) {
        return MemorySegment.ofAddress(starAddress(segment), layout.byteSize(), scope);
    }

    /**
     * Create a pointer in the memory.
     * @param scope scope of the segment
     * @return segment stores a pointer
     */
    public static MemorySegment address(ResourceScope scope) {
        return MemorySegment.allocateNative(ADDRESS, scope);
    }

    /**
     * Create a char array (or a string) in the memory.
     * @param length length of the char array
     * @param scope scope of the segment
     * @return segment stores a char array with certain length
     */
    public static MemorySegment string(long length, ResourceScope scope) {
        return MemorySegment.allocateNative(length * JAVA_CHAR.byteSize(), scope);
    }

    /**
     * Create a char array (or string) in the memory and store the string.
     * @param str string to store
     * @param scope scope of the segment
     * @return segment stores a char array contains the string
     */
    public static MemorySegment string(String str, ResourceScope scope) {
        MemorySegment segment = string(str.getBytes(StandardCharsets.UTF_8).length + 1, scope);
        segment.setUtf8String(0, str);
        return segment;
    }
}
