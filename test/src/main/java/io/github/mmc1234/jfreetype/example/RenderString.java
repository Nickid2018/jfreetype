package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FreeType;
import io.github.mmc1234.jfreetype.easyfont.CharInfo;
import io.github.mmc1234.jfreetype.easyfont.EasyFont;
import io.github.mmc1234.jfreetype.easyfont.EasyFontLibrary;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Collectors;

public class RenderString {

    private static int x = 0;

    public static void main(String[] args) throws Exception {
        FreeType.load();
        try (EasyFontLibrary library = new EasyFontLibrary()) {
            try (EasyFont font = library.createFont("C:\\Windows\\Fonts\\msyh.ttc", 0)) {
                font.setSize(16);
                java.util.List<CharInfo> infos = font.getCharInfoStream("KomeijiKoishi").collect(Collectors.toList());
                int length = infos.stream().mapToInt(CharInfo::width).sum();
                int height = infos.stream().mapToInt(CharInfo::height).max().getAsInt();
                int baseline = height + Math.toIntExact(infos.stream().mapToLong(CharInfo::minY).min().getAsLong());
                BufferedImage image = new BufferedImage(length, height, BufferedImage.TYPE_INT_BGR);
                Graphics2D graphics = image.createGraphics();
                infos.forEach(charInfo -> {
                    graphics.drawImage(EasyFont.charInfoToBitmap(charInfo), x, Math.toIntExact(baseline - charInfo.maxY()), null);
                    x += charInfo.width();
                });
                ImageIO.write(image, "png", new File("D:\\test.png"));
            }
        }
    }
}
