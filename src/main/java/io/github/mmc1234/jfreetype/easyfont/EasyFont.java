package io.github.mmc1234.jfreetype.easyfont;

import io.github.mmc1234.jfreetype.core.FTFace;
import io.github.mmc1234.jfreetype.core.FTGlyphSlot;
import io.github.mmc1234.jfreetype.core.FTRenderMode;
import io.github.mmc1234.jfreetype.glyph.FTBitmapGlyph;
import io.github.mmc1234.jfreetype.image.FTBBox;
import io.github.mmc1234.jfreetype.image.FTBitmap;
import io.github.mmc1234.jfreetype.image.FTPixelMode;
import io.github.mmc1234.jfreetype.util.Scope;
import jdk.incubator.foreign.*;

import java.awt.image.BufferedImage;
import java.util.stream.Stream;

import static io.github.mmc1234.jfreetype.core.FTErrors.*;
import static io.github.mmc1234.jfreetype.core.FreeTypeFace.*;
import static io.github.mmc1234.jfreetype.core.FreeTypeGlyph.*;
import static io.github.mmc1234.jfreetype.glyph.FTGlyphBBoxMode.*;
import static io.github.mmc1234.jfreetype.util.VarUtils.*;

/**
 * A quicker way to use FreeType.
 */
public class EasyFont implements AutoCloseable {

    private final MemorySegment face;
    private final Scope scope;
    private final CharInfoMap charInfos;
    private int size = 32;

    /**
     * Create an instance.
     * @param face instance of face
     * @param scope scope of font
     */
    EasyFont(MemorySegment face, Scope scope) {
        this.face = face;
        this.scope = scope;
        charInfos = new CharInfoMap();
        checkCode(FTSetPixelSizes(face.address(), 0, size));
        FTSetTransform(face.address(), MemoryAddress.NULL, MemoryAddress.NULL);
    }

    /**
     * Set pixel width & height.
     * @param size size to set the font
     */
    public void setSize(int size) {
        checkCode(FTSetPixelSizes(face.address(), 0, size));
        this.size = size;
    }

    private MemorySegment loadChar(int index) {
        checkCode(FTLoadGlyph(face.address(), index, FT_LOAD_NO_BITMAP | FT_LOAD_FORCE_AUTOHINT));
        FTFace.GLYPH.get(face);
        MemorySegment slot = scope.getSegment(FTFace.GLYPH.handle(), face, FTGlyphSlot.STRUCT_LAYOUT);
        MemorySegment ptrGlyph = scope.newAddress();
        checkCode(FTGetGlyph(slot.address(), ptrGlyph));
        return ptrGlyph;
    }

    /**
     * Get ascender of the font
     * @return the ascender
     */
    public short getAscender() {
        return FTFace.ASCENDER.get(face);
    }

    /**
     * Get descender of the font.
     * @return the descender
     */
    public short getDescender() {
        return FTFace.DESCENDER.get(face);
    }

    /**
     * Get index of the char.
     * @param codepoint a char
     * @return index of the char
     */
    public int getCharIndex(int codepoint) {
        return FTGetCharIndex(face.address(), codepoint);
    }

    /**
     * Get information of a codepoint.
     * @param codepoint a char
     * @return information of the char
     */
    public CharInfo getCharInfo(int codepoint) {
        CharInfo saved = charInfos.getCharInfo(codepoint, size);
        if (saved != null)
            return saved;

        int charIndex = getCharIndex(codepoint);
        MemorySegment ptrGlyph = loadChar(charIndex);

        MemorySegment bbox = scope.newSegment(FTBBox.STRUCT_LAYOUT);
        FTGlyphGetCBox(starAddress(ptrGlyph), FT_GLYPH_BBOX_TRUNCATE.value(), bbox);
        long minX = FTBBox.X_MIN.get(bbox);
        long minY = FTBBox.Y_MIN.get(bbox);
        long maxX = FTBBox.X_MAX.get(bbox);
        long maxY = FTBBox.Y_MAX.get(bbox);
        int width = Math.toIntExact(maxX - minX);
        int height = Math.toIntExact(maxY - minY);

        CharInfo info = new CharInfo(codepoint, charIndex, size, width, height, minX, minY, maxX, maxY, null);
        charInfos.putCharInfo(info);
        return info;
    }

    /**
     * Get information of a codepoint and render it.
     * @param codepoint a char
     * @return information of the char
     */
    public CharInfo getCharInfoAndRender(int codepoint) {
        CharInfo saved = charInfos.getCharInfo(codepoint, size);
        if (saved != null && saved.bitmap() != null)
            return saved;

        int charIndex = getCharIndex(codepoint);
        MemorySegment ptrGlyph = loadChar(charIndex);

        MemorySegment slot = scope.getSegment(FTFace.GLYPH.handle(), face, FTGlyphSlot.STRUCT_LAYOUT);
        checkCode(FTRenderGlyph(slot.address(), FTRenderMode.FT_RENDER_MODE_NORMAL));
        checkCode(FTGlyphToBitmap(ptrGlyph, FTRenderMode.FT_RENDER_MODE_NORMAL, MemoryAddress.NULL, true));

        MemorySegment bbox = scope.newSegment(FTBBox.STRUCT_LAYOUT);
        FTGlyphGetCBox(starAddress(ptrGlyph), FT_GLYPH_BBOX_TRUNCATE.value(), bbox);
        long minX = FTBBox.X_MIN.get(bbox);
        long minY = FTBBox.Y_MIN.get(bbox);
        long maxX = FTBBox.X_MAX.get(bbox);
        long maxY = FTBBox.Y_MAX.get(bbox);
        int width = Math.toIntExact(maxX - minX);
        int height = Math.toIntExact(maxY - minY);

        MemorySegment bitmap = scope.getSegment(FTBitmapGlyph.BITMAP.handle(),
                star(ptrGlyph, FTBitmapGlyph.STRUCT_LAYOUT), FTBitmap.STRUCT_LAYOUT);
        if(FTBitmap.PIXEL_MODE.get(bitmap) != FTPixelMode.FT_PIXEL_MODE_GRAY.value())
            throw new RuntimeException("Invalid pixel mode");

        MemoryAddress buffer = FTBitmap.BUFFER.get(bitmap);
        int pitch = FTBitmap.PITCH.get(bitmap);

        byte[][] luminanceArray = new byte[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                byte gray = buffer.get(ValueLayout.JAVA_BYTE, ((long) i * pitch) + j);
                luminanceArray[i][j] = gray;
            }

        CharInfo info = new CharInfo(codepoint, charIndex, size,
                width, height, minX, minY, maxX, maxY, luminanceArray);
        charInfos.putCharInfo(info);
        return info;
    }

    /**
     * Get information of chars in the string.
     * @param str string to get information
     * @return stream of the information
     */
    public Stream<CharInfo> getCharInfoStream(String str, boolean render) {
        return str.codePoints().mapToObj(render ? this::getCharInfoAndRender : this::getCharInfo);
    }

    /**
     * Get a bitmap of a char.
     * @param codepoint char to get the bitmap
     * @return bitmap of the char
     */
    public BufferedImage getCharBitmap(int codepoint) {
        return charInfoToBitmap(getCharInfoAndRender(codepoint));
    }

    /**
     * Get bitmaps of chars in the string.
     * @param str string to get information
     * @return stream of the bitmaps
     */
    public Stream<BufferedImage> getCharBitmaps(String str) {
        return str.codePoints().mapToObj(this::getCharBitmap);
    }

    /**
     * Parse a CharInfo into a bitmap.
     * @param info information of the char
     * @return bitmap of the char
     */
    public static BufferedImage charInfoToBitmap(CharInfo info) {
        BufferedImage image = new BufferedImage(info.width(), info.height(), BufferedImage.TYPE_INT_BGR);
        for (int i = 0; i < info.height(); i++)
            for (int j = 0; j < info.width(); j++) {
                int gray = info.bitmap()[i][j] & 0xFF;
                image.setRGB(j, i, gray | (gray << 8) | (gray << 16));
            }
        return image;
    }

    /**
     * Get char information map.
     * @return map of the chars
     */
    public CharInfoMap getCharInfos() {
        return charInfos;
    }

    /**
     * Face instance.
     * @return face instance
     */
    public MemorySegment getFace() {
        return face;
    }

    @Override
    public void close() {
        checkCode(FTDoneFace(face.address()));
    }
}
