package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FreeType;
import io.github.mmc1234.jfreetype.easyfont.CharInfo;
import io.github.mmc1234.jfreetype.easyfont.EasyFont;
import io.github.mmc1234.jfreetype.easyfont.EasyFontLibrary;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class RenderStringTest {

    @Test
    public void main() throws Exception {
        FreeType.load();
        int[] x = {0};
        try (EasyFontLibrary library = new EasyFontLibrary()) {
            try (EasyFont font = library.createFont("C:\\Windows\\Fonts\\msyh.ttc", 0)) {
                font.setSize(16);
                java.util.List<CharInfo> infos = font.getCharInfoStream("KomeijiKoishi", true).toList();
                int length = infos.stream().mapToInt(CharInfo::width).sum();
                int height = infos.stream().mapToInt(CharInfo::height).max().getAsInt();
                int baseline = height + Math.toIntExact(infos.stream().mapToLong(CharInfo::minY).min().getAsLong());
                BufferedImage image = new BufferedImage(length, height, BufferedImage.TYPE_INT_BGR);
                Graphics2D graphics = image.createGraphics();
                infos.forEach(charInfo -> {
                    graphics.drawImage(EasyFont.charInfoToBitmap(charInfo), x[0], Math.toIntExact(baseline - charInfo.maxY()), null);
                    x[0] += charInfo.width();
                });
                ImageIO.write(image, "png", new File("D:\\test.png"));
            }
        }
    }
}
