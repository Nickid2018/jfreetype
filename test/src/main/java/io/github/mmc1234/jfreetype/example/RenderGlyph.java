package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FTFace;
import io.github.mmc1234.jfreetype.core.FTGlyphSlot;
import io.github.mmc1234.jfreetype.core.FTRenderMode;
import io.github.mmc1234.jfreetype.core.FreeType;
import io.github.mmc1234.jfreetype.glyph.FTBitmapGlyph;
import io.github.mmc1234.jfreetype.image.FTBBox;
import io.github.mmc1234.jfreetype.image.FTBitmap;
import io.github.mmc1234.jfreetype.image.FTPixelMode;
import io.github.mmc1234.jfreetype.image.FTVector;
import io.github.mmc1234.jfreetype.util.VarUtils;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ValueLayout;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.VarHandle;

import static io.github.mmc1234.jfreetype.core.FreeTypeGlyph.*;
import static io.github.mmc1234.jfreetype.core.FreeTypeFace.*;
import static io.github.mmc1234.jfreetype.core.FreeTypeLibrary.*;
import static io.github.mmc1234.jfreetype.glyph.FTGlyphBBoxMode.FT_GLYPH_BBOX_TRUNCATE;
import static io.github.mmc1234.jfreetype.util.VarUtils.*;

public class RenderGlyph {

    public static void main(String[] args) throws IOException {
        FreeType.load();
        MemorySegment ptrLibrary = newAddress();
        Asserts.checkErrorCode(FTInitFreeType(ptrLibrary));
        MemorySegment ptrFace = newAddress();
        Asserts.checkErrorCode(FTNewFace(starAddress(ptrLibrary),
                newString("C:\\Windows\\Fonts\\msyh.ttc").address(), 0, ptrFace));
        MemorySegment face = star(ptrFace, FTFace.STRUCT_LAYOUT);
        FTSetPixelSizes(face.address(), 0, 32);
        System.out.println(VarUtils.getString(FTFace.FAMILY_NAME, face));
        System.out.println(VarUtils.getString(FTFace.STYLE_NAME, face));
        FTSetTransform(face.address(), MemoryAddress.NULL, MemoryAddress.NULL);
        int charIndex = FTGetCharIndex(face.address(), '淦');
        Asserts.checkErrorCode(FTLoadGlyph(face.address(), charIndex, FT_LOAD_NO_BITMAP | FT_LOAD_FORCE_AUTOHINT));
        MemorySegment ptrGlyph = newAddress();
        MemorySegment slot = getSegment(FTFace.GLYPH, face, FTGlyphSlot.STRUCT_LAYOUT);
        Asserts.checkErrorCode(FTGetGlyph(slot.address(), ptrGlyph));
        Asserts.checkErrorCode(FTRenderGlyph(slot.address(), FTRenderMode.FT_RENDER_MODE_NORMAL));
        Asserts.checkErrorCode(FTGlyphToBitmap(ptrGlyph, FTRenderMode.FT_RENDER_MODE_NORMAL, MemoryAddress.NULL, true));
        MemorySegment bitmap = getSegment(FTBitmapGlyph.BITMAP, star(ptrGlyph, FTBitmapGlyph.STRUCT_LAYOUT), FTBitmap.STRUCT_LAYOUT);
        MemoryAddress buffer = getAddress(FTBitmap.BUFFER, bitmap);
        int rows = getInt(FTBitmap.ROWS, bitmap);
        int width = getInt(FTBitmap.WIDTH, bitmap);
        int pitch = getInt(FTBitmap.PITCH, bitmap);
        int pixelMode = getInt(FTBitmap.PIXEL_MODE, bitmap);
        System.out.printf("rows = %d%nwidth = %d%n", rows, width);
        BufferedImage image = new BufferedImage(width, rows, BufferedImage.TYPE_INT_BGR);
        if (pixelMode == FTPixelMode.FT_PIXEL_MODE_GRAY.value())
            for (int i = 0; i < rows; i++)
                for (int j = 0; j < width; j++) {
                   int gray = buffer.get(ValueLayout.JAVA_BYTE, ((long) i * pitch) + j) & 0xFF;
                   image.setRGB(j, i, gray | (gray << 8) | (gray << 16));
                }
        MemorySegment ptrBBox = newAddress();
        FTGlyphGetCBox(starAddress(ptrGlyph), FT_GLYPH_BBOX_TRUNCATE.value(), ptrBBox);
        MemorySegment bbox = star(ptrBBox, FTBBox.STRUCT_LAYOUT);
        long minX = getLong(FTBBox.X_MIN, bbox);
        long minY = getLong(FTBBox.Y_MIN, bbox);
        long maxX = getLong(FTBBox.X_MAX, bbox);
        long maxY = getLong(FTBBox.Y_MAX, bbox);
        System.out.printf("min_x = %d%nmin_y = %d%nmax_x = %d%nmax_y = %d%n", minX, minY, maxX, maxY);

        FTDoneFace(face.address());
        FTDoneFreeType(starAddress(ptrLibrary));
        ImageIO.write(image, "png", new File("D:\\test.png"));
    }
}
