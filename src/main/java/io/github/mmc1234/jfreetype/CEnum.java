package io.github.mmc1234.jfreetype;

import java.util.function.IntSupplier;

/**
 * An interface represents enums in C.
 */
public interface CEnum extends IntSupplier {

    /**
     * Return number indicating the enum constant
     * @return a number
     */
    default int value() {
        if(this instanceof Enum<?> e) {
            return e.ordinal();
        }
        throw new UnsupportedOperationException();
    }

    default int getAsInt() {
        return value();
    }
}
