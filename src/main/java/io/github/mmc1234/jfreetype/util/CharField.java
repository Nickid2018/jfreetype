package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.VarHandle;

public record CharField(VarHandle handle) {
    public char get(MemorySegment segment, int index) {
        return VarUtils.getChar(handle, segment, index);
    }

    public char get(MemorySegment segment) {
        return VarUtils.getChar(handle, segment);
    }

    public byte getRaw(MemorySegment segment, int index) {
        return VarUtils.getByte(handle, segment, index);
    }

    public byte getRaw(MemorySegment segment) {
        return VarUtils.getByte(handle, segment);
    }
}