package com.github.mmc1234.jfreetype;

import jdk.incubator.foreign.*;

import java.lang.invoke.VarHandle;

public interface FTGeneric {
    MemoryLayout STRUCT_LAYOUT = MemoryLayout.structLayout(
            ValueLayout.ADDRESS.withName("data"), // void*
            ValueLayout.ADDRESS.withName("finalizer") // FTGenericFinalizer
    );
    MemoryLayout SEQUENCE_LAYOUT = MemoryLayout.sequenceLayout(STRUCT_LAYOUT);

    VarHandle DATA = SEQUENCE_LAYOUT.varHandle(MemoryLayout.PathElement.sequenceElement(),MemoryLayout.PathElement.groupElement("data"));
    VarHandle FINALIZER = SEQUENCE_LAYOUT.varHandle(MemoryLayout.PathElement.sequenceElement(),MemoryLayout.PathElement.groupElement("finalizer"));

    public static MemoryAddress data(MemorySegment s, int index) {
        return (MemoryAddress) FINALIZER.get(s, index);
    }

    public static void main(String[] args) {
        var s = MemorySegment.allocateNative(MemoryLayout.sequenceLayout(4, STRUCT_LAYOUT), ResourceScope.globalScope());
        System.out.println(data(s, 1).toRawLongValue());
    }
}
