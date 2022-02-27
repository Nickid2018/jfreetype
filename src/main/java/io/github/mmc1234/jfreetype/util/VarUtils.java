package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static jdk.incubator.foreign.ValueLayout.*;

/**
 * Utility class for variable operations.
 */
public class VarUtils {

    /**
     * C: bool true = 1
     */
    public static final int TRUE = 1;

    /**
     * C: bool false = 0
     */
    public static final int FALSE = 0;

    /**
     * nullptr
     */
    public static final int NULL = 0;

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
     * Get newAddress from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return newAddress in the field
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
     * Get boolean from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @return char in the field
     */
    public static boolean getBoolean(VarHandle handle, MemorySegment segment, int index) {
        return (boolean) handle.get(segment, index);
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
     * Get newAddress from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return newAddress in the field
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
     * Get boolean from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @return boolean in the field
     */
    public static boolean getBoolean(VarHandle handle, MemorySegment segment) {
        return (boolean) handle.get(segment, 0);
    }

    /**
     * Dereference the newAddress from a segment.
     * @param segment segment to operate
     * @return newAddress in the segment
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
    public static MemorySegment newAddress(ResourceScope scope) {
        return MemorySegment.allocateNative(ADDRESS, scope);
    }

    /**
     * Create a char array (or a string) in the memory.
     * @param length length of the char array
     * @param scope scope of the segment
     * @return segment stores a char array with certain length
     */
    public static MemorySegment newString(long length, ResourceScope scope) {
        return MemorySegment.allocateNative(length * JAVA_CHAR.byteSize(), scope);
    }

    /**
     * Create a char array (or string) in the memory and store the string.
     * @param str string to store
     * @param scope scope of the segment
     * @return segment stores a char array contains the string
     */
    public static MemorySegment newString(String str, ResourceScope scope) {
        // UTF-8 bytes <= chars * 4
        MemorySegment segment = newString(str.length() * 4L + 1, scope);
        segment.setUtf8String(0, str);
        return segment;
    }

    /**
     * Get the object that the segment is pointing to using global scope.
     * @param segment segment to operate
     * @param layout layout of the object
     * @return object which is pointed
     */
    public static MemorySegment star(MemorySegment segment, MemoryLayout layout) {
        return MemorySegment.ofAddress(starAddress(segment), layout.byteSize(), ResourceScope.globalScope());
    }

    /**
     * Create a pointer in the memory using global scope.
     * @return segment stores a pointer
     */
    public static MemorySegment newAddress() {
        return MemorySegment.allocateNative(ADDRESS, ResourceScope.globalScope());
    }

    /**
     * Create a char array (or a string) in the memory using global scope.
     * @param length length of the char array
     * @return segment stores a char array with certain length
     */
    public static MemorySegment newString(long length) {
        return MemorySegment.allocateNative(length * JAVA_CHAR.byteSize(), ResourceScope.globalScope());
    }
    /**
     * Create a char array (or string) in the memory and store the string using global scope.
     * @param str string to store
     * @return segment stores a char array contains the string
     */
    public static MemorySegment newString(String str) {
        return newString(str, ResourceScope.globalScope());
    }

    /**
     * Create a segment can store an int.
     * @param scope scope of segment
     * @return a segment
     */
    public static MemorySegment newInt(ResourceScope scope) {
        return MemorySegment.allocateNative(JAVA_INT, scope);
    }

    /**
     * Create a segment can store a long.
     * @param scope scope of segment
     * @return a segment
     */
    public static MemorySegment newLong(ResourceScope scope) {
        return MemorySegment.allocateNative(JAVA_LONG, scope);
    }

    /**
     * Create a segment can store a short.
     * @param scope scope of segment
     * @return a segment
     */
    public static MemorySegment newShort(ResourceScope scope) {
        return MemorySegment.allocateNative(JAVA_SHORT, scope);
    }

    /**
     * Create a segment can store a char.
     * @param scope scope of segment
     * @return a segment
     */
    public static MemorySegment newChar(ResourceScope scope) {
        return MemorySegment.allocateNative(JAVA_CHAR, scope);
    }

    /**
     * Create a segment can store a byte.
     * @param scope scope of segment
     * @return a segment
     */
    public static MemorySegment newByte(ResourceScope scope) {
        return MemorySegment.allocateNative(JAVA_BYTE, scope);
    }

    /**
     * Create a segment can store a float.
     * @param scope scope of segment
     * @return a segment
     */
    public static MemorySegment newFloat(ResourceScope scope) {
        return MemorySegment.allocateNative(JAVA_FLOAT, scope);
    }

    /**
     * Create a segment can store a double.
     * @param scope scope of segment
     * @return a segment
     */
    public static MemorySegment newDouble(ResourceScope scope) {
        return MemorySegment.allocateNative(JAVA_DOUBLE, scope);
    }

    /**
     * Create a segment can store a boolean.
     * @param scope scope of segment
     * @return a segment
     */
    public static MemorySegment newBoolean(ResourceScope scope) {
        return MemorySegment.allocateNative(JAVA_BOOLEAN, scope);
    }

    /**
     * Create a segment can store an int using global scope.
     * @return a segment
     */
    public static MemorySegment newInt() {
        return MemorySegment.allocateNative(JAVA_INT, ResourceScope.globalScope());
    }

    /**
     * Create a segment can store a long using global scope.
     * @return a segment
     */
    public static MemorySegment newLong() {
        return MemorySegment.allocateNative(JAVA_LONG, ResourceScope.globalScope());
    }

    /**
     * Create a segment can store a short using global scope.
     * @return a segment
     */
    public static MemorySegment newShort() {
        return MemorySegment.allocateNative(JAVA_SHORT, ResourceScope.globalScope());
    }

    /**
     * Create a segment can store a char using global scope.
     * @return a segment
     */
    public static MemorySegment newChar() {
        return MemorySegment.allocateNative(JAVA_CHAR, ResourceScope.globalScope());
    }

    /**
     * Create a segment can store a byte using global scope.
     * @return a segment
     */
    public static MemorySegment newByte() {
        return MemorySegment.allocateNative(JAVA_BYTE, ResourceScope.globalScope());
    }

    /**
     * Create a segment can store a float using global scope.
     * @return a segment
     */
    public static MemorySegment newFloat() {
        return MemorySegment.allocateNative(JAVA_FLOAT, ResourceScope.globalScope());
    }

    /**
     * Create a segment can store a double using global scope.
     * @return a segment
     */
    public static MemorySegment newDouble() {
        return MemorySegment.allocateNative(JAVA_DOUBLE, ResourceScope.globalScope());
    }

    /**
     * Create a segment can store a boolean using global scope.
     * @return a segment
     */
    public static MemorySegment newBoolean() {
        return MemorySegment.allocateNative(JAVA_BOOLEAN, ResourceScope.globalScope());
    }

    /**
     * Get integer from the segment.
     * @param segment segment to operate
     * @param index index of the element
     * @return integer in the segment
     */
    public static int getInt(MemorySegment segment, int index) {
        return segment.getAtIndex(JAVA_INT, index);
    }

    /**
     * Get short from the segment.
     * @param segment segment to operate
     * @param index index of the element
     * @return short in the segment
     */
    public static short getShort(MemorySegment segment, int index) {
        return segment.getAtIndex(JAVA_SHORT, index);
    }

    /**
     * Get long from the segment.
     * @param segment segment to operate
     * @param index index of the element
     * @return long in the segment
     */
    public static long getLong(MemorySegment segment, int index) {
        return segment.getAtIndex(JAVA_LONG, index);
    }

    /**
     * Get byte from the segment.
     * @param segment segment to operate
     * @param index index of the element
     * @return byte in the segment
     */
    public static byte getByte(MemorySegment segment, int index) {
        return segment.get(JAVA_BYTE, index);
    }

    /**
     * Get float from the segment.
     * @param segment segment to operate
     * @param index index of the element
     * @return float in the segment
     */
    public static float getFloat(MemorySegment segment, int index) {
        return segment.getAtIndex(JAVA_FLOAT, index);
    }

    /**
     * Get double from the segment.
     * @param segment segment to operate
     * @param index index of the element
     * @return double in the segment
     */
    public static double getDouble(MemorySegment segment, int index) {
        return segment.getAtIndex(JAVA_DOUBLE, index);
    }

    /**
     * Get char from the segment.
     * @param segment segment to operate
     * @param index index of the element
     * @return char in the segment
     */
    public static char getChar(MemorySegment segment, int index) {
        return segment.getAtIndex(JAVA_CHAR, index);
    }

    /**
     * Get boolean from the segment.
     * @param segment segment to operate
     * @param index index of the element
     * @return boolean in the segment
     */
    public static boolean getBoolean(MemorySegment segment, int index) {
        return segment.get(JAVA_BOOLEAN, index);
    }

    /**
     * Get integer from the segment.
     * @param segment segment to operate
     * @return integer in the segment
     */
    public static int getInt(MemorySegment segment) {
        return segment.get(JAVA_INT, 0);
    }

    /**
     * Get short from the segment.
     * @param segment segment to operate
     * @return short in the segment
     */
    public static short getShort(MemorySegment segment) {
        return segment.get(JAVA_SHORT, 0);
    }

    /**
     * Get long from the segment.
     * @param segment segment to operate
     * @return long in the segment
     */
    public static long getLong(MemorySegment segment) {
        return segment.get(JAVA_LONG, 0);
    }

    /**
     * Get byte from the segment.
     * @param segment segment to operate
     * @return byte in the segment
     */
    public static byte getByte(MemorySegment segment) {
        return segment.get(JAVA_BYTE, 0);
    }

    /**
     * Get float from the segment.
     * @param segment segment to operate
     * @return float in the segment
     */
    public static float getFloat(MemorySegment segment) {
        return segment.get(JAVA_FLOAT, 0);
    }

    /**
     * Get double from the segment.
     * @param segment segment to operate
     * @return double in the segment
     */
    public static double getDouble(MemorySegment segment) {
        return segment.get(JAVA_DOUBLE, 0);
    }

    /**
     * Get char from the segment.
     * @param segment segment to operate
     * @return char in the segment
     */
    public static char getChar(MemorySegment segment) {
        return segment.get(JAVA_CHAR, 0);
    }

    /**
     * Get boolean from the segment.
     * @param segment segment to operate
     * @return boolean in the segment
     */
    public static boolean getBoolean(MemorySegment segment) {
        return segment.get(JAVA_BOOLEAN, 0);
    }
}
