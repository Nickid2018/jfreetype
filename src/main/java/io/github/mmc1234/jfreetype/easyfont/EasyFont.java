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
import static io.github.mmc1234.jfreetype.glyph.FTGlyphBBoxMode.FT_GLYPH_BBOX_TRUNCATE;
import static io.github.mmc1234.jfreetype.util.VarUtils.*;

public class EasyFont implements AutoCloseable {

    private final MemorySegment face;
    private final Scope scope;
    private int size = 32;

    public EasyFont(MemorySegment face, Scope scope) {
        this.face = face;
        this.scope = scope;
        checkCode(FTSetPixelSizes(face.address(), 0, size));
        FTSetTransform(face.address(), MemoryAddress.NULL, MemoryAddress.NULL);
    }

    public void setSize(int size) {
        checkCode(FTSetPixelSizes(face.address(), 0, size));
        this.size = size;
    }

    public CharInfo getCharInfo(int codepoint) {
        int charIndex = FTGetCharIndex(face.address(), codepoint);
        checkCode(FTLoadGlyph(face.address(), charIndex, FT_LOAD_NO_BITMAP | FT_LOAD_FORCE_AUTOHINT));

        MemorySegment ptrGlyph = scope.newAddress();
        MemorySegment slot = scope.getSegment(FTFace.GLYPH, face, FTGlyphSlot.STRUCT_LAYOUT);
        checkCode(FTGetGlyph(slot.address(), ptrGlyph));
        checkCode(FTRenderGlyph(slot.address(), FTRenderMode.FT_RENDER_MODE_NORMAL));
        checkCode(FTGlyphToBitmap(ptrGlyph, FTRenderMode.FT_RENDER_MODE_NORMAL, MemoryAddress.NULL, true));

        MemorySegment bbox = scope.newSegment(FTBBox.STRUCT_LAYOUT);
        FTGlyphGetCBox(starAddress(ptrGlyph), FT_GLYPH_BBOX_TRUNCATE.value(), bbox);
        long maxX = getLong(FTBBox.X_MAX, bbox);
        long maxY = getLong(FTBBox.Y_MAX, bbox);
        long minX = getLong(FTBBox.X_MIN, bbox);
        long minY = getLong(FTBBox.Y_MIN, bbox);

        MemorySegment bitmap = scope.getSegment(FTBitmapGlyph.BITMAP,
                star(ptrGlyph, FTBitmapGlyph.STRUCT_LAYOUT), FTBitmap.STRUCT_LAYOUT);
        if(getInt(FTBitmap.PIXEL_MODE, bitmap) != FTPixelMode.FT_PIXEL_MODE_GRAY.value())
            throw new RuntimeException("Invalid pixel mode");

        MemoryAddress buffer = getAddress(FTBitmap.BUFFER, bitmap);
        int pitch = getInt(FTBitmap.PITCH, bitmap);
        int width = Math.toIntExact(maxX - minX);
        int height = Math.toIntExact(maxY - minY);

        byte[][] luminanceArray = new byte[height][width];
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++) {
                byte gray = buffer.get(ValueLayout.JAVA_BYTE, ((long) i * pitch) + j);
                luminanceArray[i][j] = gray;
            }

        return new CharInfo(codepoint, size, width, height,
                minX, minY, maxX, maxY, luminanceArray);
    }

    public Stream<CharInfo> getCharInfoStream(String str) {
        return str.codePoints().mapToObj(this::getCharInfo);
    }

    public BufferedImage getCharBitmap(int codepoint) {
        return charInfoToBitmap(getCharInfo(codepoint));
    }

    public Stream<BufferedImage> getCharBitmaps(String str) {
        return str.codePoints().mapToObj(this::getCharBitmap);
    }

    public static BufferedImage charInfoToBitmap(CharInfo info) {
        BufferedImage image = new BufferedImage(info.width(), info.height(), BufferedImage.TYPE_INT_BGR);
        for (int i = 0; i < info.height(); i++)
            for (int j = 0; j < info.width(); j++) {
                int gray = info.luminance()[i][j] & 0xFF;
                image.setRGB(j, i, gray | (gray << 8) | (gray << 16));
            }
        return image;
    }

    public MemorySegment getFace() {
        return face;
    }

    @Override
    public void close() {
        checkCode(FTDoneFace(face.address()));
    }
}
