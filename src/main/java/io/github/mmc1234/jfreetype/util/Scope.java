package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;

import static jdk.incubator.foreign.ValueLayout.*;

/**
 * A class for using scope.
 */
public class Scope implements AutoCloseable {

    private final ResourceScope scope;

    private Scope() {
        scope = ResourceScope.newConfinedScope();
    }

    /**
     * Create a scope. The statement should be used in try-with-resource struct.
     * @return a new scope
     */
    public static Scope pushScope() {
        return new Scope();
    }

    /**
     * Create a pointer in the memory using the scope.
     * @return segment stores a pointer
     */
    public MemorySegment newAddress() {
        return MemorySegment.allocateNative(ADDRESS, scope);
    }

    /**
     * Create a pointer in the memory using the scope.
     * @param len length of array
     * @return segment stores a pointer
     */
    public MemorySegment newAddressArray(int len) {
        return MemorySegment.allocateNative(ADDRESS.byteSize() * len, scope);
    }

    /**
     * Create a segment can store an int using the scope.
     * @return a segment
     */
    public MemorySegment newInt() {
        return MemorySegment.allocateNative(JAVA_INT, scope);
    }

    /**
     * Create a segment can store a long using the scope.
     * @return a segment
     */
    public MemorySegment newLong() {
        return MemorySegment.allocateNative(JAVA_LONG, scope);
    }

    /**
     * Create a segment can store a short using the scope.
     * @return a segment
     */
    public MemorySegment newShort() {
        return MemorySegment.allocateNative(JAVA_SHORT, scope);
    }

    /**
     * Create a segment can store a char using the scope.
     * @return a segment
     */
    public MemorySegment newChar() {
        return MemorySegment.allocateNative(JAVA_CHAR, scope);
    }

    /**
     * Create a segment can store a byte using the scope.
     * @return a segment
     */
    public MemorySegment newByte() {
        return MemorySegment.allocateNative(JAVA_BYTE, scope);
    }

    /**
     * Create a segment can store a float using the scope.
     * @return a segment
     */
    public MemorySegment newFloat() {
        return MemorySegment.allocateNative(JAVA_FLOAT, scope);
    }

    /**
     * Create a segment can store a double using the scope.
     * @return a segment
     */
    public MemorySegment newDouble() {
        return MemorySegment.allocateNative(JAVA_DOUBLE, scope);
    }

    /**
     * Create a segment can store a boolean using the scope.
     * @return a segment
     */
    public MemorySegment newBoolean() {
        return MemorySegment.allocateNative(JAVA_BOOLEAN, scope);
    }

    /**
     * Create a segment can store an int array using the scope.
     * @param len length of the array
     * @return a segment
     */
    public MemorySegment newIntArray(int len) {
        return MemorySegment.allocateNative(JAVA_INT.byteSize() * len, scope);
    }

    /**
     * Create a segment can store a long array using the scope.
     * @param len length of the array
     * @return a segment
     */
    public MemorySegment newLongArray(int len) {
        return MemorySegment.allocateNative(JAVA_LONG.byteSize() * len, scope);
    }

    /**
     * Create a segment can store a short array using the scope.
     * @param len length of the array
     * @return a segment
     */
    public MemorySegment newShortArray(int len) {
        return MemorySegment.allocateNative(JAVA_SHORT.byteSize() * len, scope);
    }

    /**
     * Create a segment can store a char array using the scope.
     * @param len length of the array
     * @return a segment
     */
    public MemorySegment newCharArray(int len) {
        return MemorySegment.allocateNative(JAVA_CHAR.byteSize() * len, scope);
    }

    /**
     * Create a segment can store a byte array using the scope.
     * @param len length of the array
     * @return a segment
     */
    public MemorySegment newByteArray(int len) {
        return MemorySegment.allocateNative(JAVA_BYTE.byteSize() * len, scope);
    }

    /**
     * Create a segment can store a float array using the scope.
     * @param len length of the array
     * @return a segment
     */
    public  MemorySegment newFloatArray(int len) {
        return MemorySegment.allocateNative(JAVA_FLOAT.byteSize() * len, scope);
    }

    /**
     * Create a segment can store a double array using the scope.
     * @param len length of the array
     * @return a segment
     */
    public MemorySegment newDoubleArray(int len) {
        return MemorySegment.allocateNative(JAVA_DOUBLE.byteSize() * len, scope);
    }

    /**
     * Create a segment can store a boolean array using the scope.
     * @param len length of the array
     * @return a segment
     */
    public MemorySegment newBooleanArray(int len) {
        return MemorySegment.allocateNative(JAVA_BOOLEAN.byteSize() * len, scope);
    }

    /**
     * Get segment from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param layout layout of the struct
     * @return segment of the field
     */
    public MemorySegment getSegment(VarHandle handle, MemorySegment segment, MemoryLayout layout) {
        MemoryAddress addr = VarUtils.getAddress(handle, segment);
        return MemorySegment.ofAddress(addr, layout.byteSize(), scope);
    }

    /**
     * Get segment from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @param layout layout of the struct
     * @return segment of the field
     */
    public MemorySegment getSegment(VarHandle handle, MemorySegment segment, int index, MemoryLayout layout) {
        MemoryAddress addr = VarUtils.getAddress(handle, segment, index);
        return MemorySegment.ofAddress(addr, layout.byteSize(), scope);
    }

    /**
     * Get segment from the field.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param index index of the element
     * @param layout layout of the struct
     * @return segment of the field
     */
    public MemorySegment getSegment(MethodHandle handle, MemorySegment segment, int index, MemoryLayout layout) {
        MemoryAddress addr = VarUtils.getAddress(handle, segment, index);
        return MemorySegment.ofAddress(addr, layout.byteSize(), scope);
    }

    /**
     * Get segment from the field using the scope.
     * @param handle handle of the field
     * @param segment segment to operate
     * @param layout layout of the struct
     * @return segment of the field
     */
    public MemorySegment getSegment(MethodHandle handle, MemorySegment segment, MemoryLayout layout) {
        MemoryAddress addr = VarUtils.getAddress(handle, segment);
        return MemorySegment.ofAddress(addr, layout.byteSize(), scope);
    }

    /**
     * Get the object that the segment is pointing to using global scope.
     * @param segment segment to operate
     * @param layout layout of the object
     * @return object which is pointed
     */
    public MemorySegment star(MemorySegment segment, MemoryLayout layout) {
        return MemorySegment.ofAddress(VarUtils.starAddress(segment), layout.byteSize(), scope);
    }

    /**
     * Create segment using the scope.
     * @param layout layout of segment
     * @return a segment
     */
    public MemorySegment newSegment(MemoryLayout layout) {
        return MemorySegment.allocateNative(layout, scope);
    }

    /**
     * Create segment array using the scope.
     * @param layout layout of segment
     * @param len length of array
     * @return a segment
     */
    public MemorySegment newSegmentArray(MemoryLayout layout, int len) {
        return MemorySegment.allocateNative(layout.byteSize() * len, scope);
    }

    /**
     * Reference an object using the scope.
     * @param segment object to be referenced
     * @return segment contains the address
     */
    public MemorySegment amp(MemorySegment segment) {
        MemorySegment seg = MemorySegment.allocateNative(ADDRESS, scope);
        seg.set(ADDRESS, 0, segment.address());
        return seg;
    }

    /**
     * Get segment at certain index using the scope.
     * @param array an segment array
     * @param layout layout of struct
     * @param index index of segment
     * @return an element
     */
    public MemorySegment getAtIndex(MemorySegment array, MemoryLayout layout, int index) {
        return MemorySegment.ofAddress(MemoryAddress.ofLong(
                array.address().toRawLongValue() + index * layout.byteSize()), layout.byteSize(), scope);
    }

    /**
     * Create segment for the pointer using the scope.
     * @param address pointer to pack
     * @return an element
     */
    public MemorySegment asSegment(MemoryAddress address) {
        MemorySegment segment = MemorySegment.allocateNative(ADDRESS, scope);
        segment.set(ADDRESS, 0, address);
        return segment;
    }

    /**
     * Create a char array (or a string) in the memory using the scope.
     * @param length length of the char array
     * @return segment stores a char array with certain length
     */
    public MemorySegment newString(long length) {
        return MemorySegment.allocateNative(length * JAVA_CHAR.byteSize(), scope);
    }
    /**
     * Create a char array (or string) in the memory and store the string using the scope.
     * @param str string to store
     * @return segment stores a char array contains the string
     */
    public MemorySegment newString(String str) {
        return VarUtils.newString(str, scope);
    }

    public ResourceScope getResourceScope() {
        return scope;
    }

    @Override
    public void close(){
        scope.close();
    }
}
