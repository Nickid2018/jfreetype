package io.github.mmc1234.jfreetype.struct;

import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.VarHandle;

public record ByteField(VarHandle handle) {
    public byte get(MemorySegment segment, int index) {
        return VarUtils.getByte(handle, segment, index);
    }

    public byte get(MemorySegment segment) {
        return VarUtils.getByte(handle, segment);
    }
}