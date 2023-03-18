package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.VarHandle;

public record BooleanField(VarHandle handle) {
    public boolean get(MemorySegment segment, int index) {
        return VarUtils.getBoolean(handle, segment, index);
    }

    public boolean get(MemorySegment segment) {
        return VarUtils.getBoolean(handle, segment);
    }
}