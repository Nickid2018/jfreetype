package io.github.mmc1234.jfreetype;

/**
 * An interface represents enums in C.
 */
public interface CEnum<T> {

    /**
     * Return number indicating the enum constant
     * @return a number
     */
    int value();

    /**
     * Values of the enum. Default implemented by {@link Enum}.
     * @return an array contains all elements in the enum
     */
    T[] values();
}
