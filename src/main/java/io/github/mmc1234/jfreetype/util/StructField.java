package io.github.mmc1234.jfreetype.util;

import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;

import java.lang.invoke.MethodHandle;

public record StructField(MethodHandle handle, MemoryLayout layout) {
    public MemorySegment get(MemorySegment segment) {
        return VarUtils.getSegment(handle, segment, layout);
    }
}
