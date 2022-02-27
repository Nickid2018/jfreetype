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
     * Get the object that the segment is pointing to using global scope.
     * @param segment segment to operate
     * @param layout layout of the object
     * @return object which is pointed
     */
    public MemorySegment star(MemorySegment segment, MemoryLayout layout) {
        return MemorySegment.ofAddress(VarUtils.starAddress(segment), layout.byteSize(), scope);
    }

    /**
     * Create segment using the scope
     * @param layout layout of segment
     * @return a segment
     */
    public MemorySegment newSegment(MemoryLayout layout) {
        return MemorySegment.allocateNative(layout, scope);
    }

    public ResourceScope getResourceScope() {
        return scope;
    }

    @Override
    public void close(){
        scope.close();
    }
}
