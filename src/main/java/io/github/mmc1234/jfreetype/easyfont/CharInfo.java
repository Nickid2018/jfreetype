package io.github.mmc1234.jfreetype.easyfont;

public record CharInfo(int codepoint,
                       int size, int width, int height,
                       long minX, long minY, long maxX, long maxY,
                       byte[][] luminance) {

    public String getCharAsString() {
        return new String(Character.toChars(codepoint));
    }
}
