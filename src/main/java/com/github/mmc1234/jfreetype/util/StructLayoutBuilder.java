package com.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.ValueLayout;

import java.lang.invoke.VarHandle;

public class StructLayoutBuilder {

    private final MemoryLayout structLayout;
    private final MemoryLayout sequenceLayout;

    public StructLayoutBuilder(String recipe, String[] names, MemoryLayout... layouts) {
        MemoryLayout[] struct = new MemoryLayout[recipe.length()];
        for (int i = 0; i < recipe.length(); i++)
            struct[i] = pick(recipe.charAt(i), names[i], layouts);
        structLayout = MemoryLayout.structLayout(struct);
        sequenceLayout = MemoryLayout.sequenceLayout(structLayout);
    }

    static MemoryLayout pick(int name, String element, MemoryLayout... layouts) {
        if (name >= '0' && name <= '9')
            return layouts[name - '0'].withName(element);
        return FunctionDescriptorUtils.pick(name).withName(element);
    }

    public MemoryLayout getStructLayout() {
        return structLayout;
    }

    public MemoryLayout getSequenceLayout() {
        return sequenceLayout;
    }

    public VarHandle varHandle(String name) {
        return sequenceLayout.varHandle(MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement(name));
    }
}
