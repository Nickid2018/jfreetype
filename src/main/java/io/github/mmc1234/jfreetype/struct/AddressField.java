package io.github.mmc1234.jfreetype.struct;

import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.lang.invoke.VarHandle;

public record AddressField(VarHandle handle, MemoryLayout layout) {
    public AddressField(VarHandle handle) {
        this(handle, null);
    }
    public MemoryAddress get(MemorySegment segment, int index) {
        return VarUtils.getAddress(handle, segment, index);
    }

    public MemoryAddress get(MemorySegment segment) {
        return VarUtils.getAddress(handle, segment);
    }
}
