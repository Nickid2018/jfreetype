package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemoryLayout;

import java.lang.invoke.VarHandle;

public class StructLayoutBuilder {

    private final MemoryLayout structLayout;
    private final MemoryLayout sequenceLayout;

    public StructLayoutBuilder(String recipe, String[] names, MemoryLayout... layouts) {
        this(recipe, names, false, layouts);
    }

    public StructLayoutBuilder(String recipe, String[] names, boolean isUnion, MemoryLayout... layouts) {
        MemoryLayout[] struct = new MemoryLayout[recipe.length()];
        for (int i = 0; i < recipe.length(); i++)
            struct[i] = pick(recipe.charAt(i), names[i], layouts);
        structLayout = isUnion ? MemoryLayout.unionLayout(struct) : MemoryLayout.structLayout(struct);
        sequenceLayout = MemoryLayout.sequenceLayout(structLayout);
    }

    static MemoryLayout pick(int name, String element, MemoryLayout... layouts) {
        if (name >= '0' && name <= '9')
            return layouts[name - '0'].withName(element);
        return FunctionDescriptorUtils.pick(name).withName(element);
    }

    /**
     * Get STRUCT_LAYOUT
     *
     * @return a layout
     */
    public MemoryLayout getStructLayout() {
        return structLayout;
    }

    /**
     * Get SEQUENCE_LAYOUT
     *
     * @return a layout
     */
    public MemoryLayout getSequenceLayout() {
        return sequenceLayout;
    }

    /**
     * Create VarHandle for single layer invocation.
     *
     * @param name path element name
     * @return a VarHandle
     */
    public VarHandle varHandle(String name) {
        return sequenceLayout.varHandle(MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement(name));
    }
}
