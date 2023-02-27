package io.github.mmc1234.jfreetype.util;

import io.github.mmc1234.jfreetype.internal.LibraryUtil;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.ValueLayout;

import java.lang.invoke.MethodHandle;

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
    public static FunctionDescriptor of(String desc, MemoryLayout... layouts) {
        MemoryLayout returnType = pick(desc.charAt(0));
        MemoryLayout[] parameters = desc.chars().skip(1).mapToObj(ch -> pick(ch, layouts)).toArray(MemoryLayout[]::new);
        return FunctionDescriptor.of(returnType, parameters);
    }

    /**
     * Create a {@link FunctionDescriptor} with certain descriptor.
     *
     * @param desc descriptor
     * @return a FunctionDescriptor
     */
    public static FunctionDescriptor ofVoid(String desc, MemoryLayout... layouts) {
        MemoryLayout[] parameters = desc.chars().mapToObj(ch -> pick(ch, layouts)).toArray(MemoryLayout[]::new);
        return FunctionDescriptor.ofVoid(parameters);
    }

    static MemoryLayout pick(int name, MemoryLayout... layouts) {
        if (name >= '0' && name <= '9')
            return layouts[name - '0'];
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
