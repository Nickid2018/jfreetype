package io.github.mmc1234.jfreetype.struct;

import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.VarHandle;

public record IntField(VarHandle handle) {
    public int get(MemorySegment segment, int index) {
        return VarUtils.getInt(handle, segment, index);
    }

    public int get(MemorySegment segment) {
        return VarUtils.getInt(handle, segment);
    }
}
