package io.github.mmc1234.jfreetype.easyfont;

import java.util.Objects;

/**
 * Information of a char.
 *
 * @param codepoint codepoint of the char
 * @param size size of the font
 * @param width width of the char
 * @param height height of the char
 * @param minX minimum x of the char box
 * @param minY minimum y of the char box
 * @param maxX maximum x of the char box
 * @param maxY maximum y of the char box
 * @param bitmap luminance bitmap, maybe null
 */
public record CharInfo(int codepoint, int charIndex,
                       int size, int width, int height,
                       long minX, long minY, long maxX, long maxY,
                       byte[][] bitmap) {

    /**
     * Get the string of codepoint.
     * @return a string
     */
    public String getCharAsString() {
        return new String(Character.toChars(codepoint));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharInfo charInfo = (CharInfo) o;
        return codepoint == charInfo.codepoint && size == charInfo.size;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codepoint, size);
    }
}
