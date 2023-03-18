package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.VarHandle;

public record LongField(VarHandle handle) {
    public long get(MemorySegment segment, int index) {
        return VarUtils.getLong(handle, segment, index);
    }

    public long get(MemorySegment segment) {
        return VarUtils.getLong(handle, segment);
    }
}
