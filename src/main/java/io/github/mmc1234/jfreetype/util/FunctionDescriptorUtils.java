package io.github.mmc1234.jfreetype.util;

import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.ValueLayout;

/**
 * Utility class for {@link FunctionDescriptor}
 */
public class FunctionDescriptorUtils {

    /**
     * Create a {@link FunctionDescriptor} with certain descriptor.
     *
     * @param desc descriptor
     * @return a FunctionDescriptor
     */
    public static FunctionDescriptor of(String desc) {
        MemoryLayout returnType = pick(desc.charAt(0));
        MemoryLayout[] parameters = desc.chars().skip(1).mapToObj(FunctionDescriptorUtils::pick).toArray(MemoryLayout[]::new);
        return FunctionDescriptor.of(returnType, parameters);
    }

    /**
     * Create a {@link FunctionDescriptor} with certain descriptor.
     *
     * @param desc descriptor
     * @return a FunctionDescriptor
     */
    public static FunctionDescriptor ofVoid(String desc) {
        MemoryLayout[] parameters = desc.chars().mapToObj(FunctionDescriptorUtils::pick).toArray(MemoryLayout[]::new);
        return FunctionDescriptor.ofVoid(parameters);
    }

    static MemoryLayout pick(int name) {
        return switch (name) {
            case 'A' -> ValueLayout.ADDRESS;
            case 'I' -> ValueLayout.JAVA_INT;
            case 'L' -> ValueLayout.JAVA_LONG;
            case 'S' -> ValueLayout.JAVA_SHORT;
            case 'B' -> ValueLayout.JAVA_BYTE;
            case 'Z' -> ValueLayout.JAVA_BOOLEAN;
            case 'D' -> ValueLayout.JAVA_DOUBLE;
            case 'C' -> ValueLayout.JAVA_CHAR;
            case 'F' -> ValueLayout.JAVA_FLOAT;
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }
}
