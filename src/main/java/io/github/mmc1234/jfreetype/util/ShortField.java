package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.VarHandle;

public record ShortField(VarHandle handle) {
    public short get(MemorySegment segment, int index) {
        return VarUtils.getShort(handle, segment, index);
    }

    public short get(MemorySegment segment) {
        return VarUtils.getShort(handle, segment);
    }
}
