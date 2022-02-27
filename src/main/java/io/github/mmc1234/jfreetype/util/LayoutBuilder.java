package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.ValueLayout;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.List;

public class LayoutBuilder {

    public static final ValueLayout ALIGNED_ADDRESS = ValueLayout.ADDRESS.withBitAlignment(64);
    public static final ValueLayout ALIGNED_INT = ValueLayout.JAVA_INT.withBitAlignment(32);
    public static final ValueLayout ALIGNED_LONG_64 = ValueLayout.JAVA_LONG.withBitAlignment(64);
    public static final ValueLayout ALIGNED_SHORT = ValueLayout.JAVA_SHORT.withBitAlignment(16);
    public static final ValueLayout ALIGNED_BYTE = ValueLayout.JAVA_BYTE.withBitAlignment(8);
    public static final ValueLayout ALIGNED_BOOLEAN = ValueLayout.JAVA_BOOLEAN.withBitAlignment(32);
    public static final ValueLayout ALIGNED_DOUBLE = ValueLayout.JAVA_DOUBLE.withBitAlignment(64);
    public static final ValueLayout ALIGNED_CHAR = ValueLayout.JAVA_CHAR.withBitAlignment(8);
    public static final ValueLayout ALIGNED_FLOAT = ValueLayout.JAVA_FLOAT.withBitAlignment(32);

    public static final ValueLayout ALIGNED_LONG =
            FunctionDescriptorUtils.sizeOfLong() == 4 ? ALIGNED_INT : ALIGNED_LONG_64;

    private final MemoryLayout groupLayout;
    private final MemoryLayout sequenceLayout;

    public LayoutBuilder(String recipe, String[] names, MemoryLayout... layouts) {
        this(recipe, names, false, layouts);
    }

    public LayoutBuilder(String recipe, String[] names, boolean isUnion, MemoryLayout... layouts) {
        MemoryLayout[] struct = new MemoryLayout[recipe.length()];
        for (int i = 0; i < recipe.length(); i++)
            struct[i] = pick(recipe.charAt(i), names[i], layouts);
        if (!isUnion) {
            List<MemoryLayout> layoutWithPadding = new ArrayList<>();
            long alignOffset = 0;
            long maxAlign = 0;
            for (MemoryLayout layout : struct) {
                long align = alignOffset % layout.byteAlignment();
                maxAlign = Math.max(layout.byteAlignment(), maxAlign);
                if (align != 0) {
                    long alignSize = layout.byteAlignment() - align;
                    alignOffset += alignSize;
                    layoutWithPadding.add(MemoryLayout.paddingLayout(alignSize * 8));
                }
                layoutWithPadding.add(layout);
                alignOffset += layout.byteSize();
            }
            if (alignOffset % maxAlign != 0)
                layoutWithPadding.add(MemoryLayout.paddingLayout((maxAlign - alignOffset % maxAlign) * 8));
            groupLayout = MemoryLayout.structLayout(layoutWithPadding.toArray(MemoryLayout[]::new));
        } else
            groupLayout = MemoryLayout.unionLayout(struct);
        sequenceLayout = MemoryLayout.sequenceLayout(groupLayout);
    }

    static MemoryLayout pick(int name, String element, MemoryLayout... layouts) {
        if (name >= '0' && name <= '9')
            return layouts[name - '0'].withName(element);
        return (switch (name) {
            case 'A' -> ALIGNED_ADDRESS;
            case 'I' -> ALIGNED_INT;
            case 'L' -> ALIGNED_LONG;
            case 'S' -> ALIGNED_SHORT;
            case 'B' -> ALIGNED_BYTE;
            case 'Z' -> ALIGNED_BOOLEAN;
            case 'D' -> ALIGNED_DOUBLE;
            case 'C' -> ALIGNED_CHAR;
            case 'F' -> ALIGNED_FLOAT;
            case 'l' -> ALIGNED_LONG_64;
            default -> throw new IllegalStateException("Unexpected value: " + name);
        }).withName(element);
    }

    /**
     * Get STRUCT_LAYOUT
     *
     * @return a layout
     */
    public MemoryLayout getGroupLayout() {
        return groupLayout;
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
