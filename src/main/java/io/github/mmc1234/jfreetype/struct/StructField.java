package io.github.mmc1234.jfreetype.struct;

import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.lang.invoke.MethodHandle;

public record StructField(MethodHandle handle, MemoryLayout layout) {
    public MemorySegment get(MemorySegment segment) {
        return VarUtils.getSegment(handle, segment, layout);
    }
}
