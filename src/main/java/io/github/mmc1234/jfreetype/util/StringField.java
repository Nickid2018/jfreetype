package io.github.mmc1234.jfreetype.util;

import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.VarHandle;

public record StringField(VarHandle handle) {
    public String get(MemorySegment segment, int index) {
        return VarUtils.getString(handle, segment, index);
    }

    public String get(MemorySegment segment) {
        return VarUtils.getString(handle, segment);
    }
}
