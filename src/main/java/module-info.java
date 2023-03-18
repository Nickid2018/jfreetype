module jfreetype {
    requires transitive jdk.incubator.foreign;
    requires java.desktop;
    exports io.github.mmc1234.jfreetype.color;
    exports io.github.mmc1234.jfreetype.types;
    exports io.github.mmc1234.jfreetype.core;
    exports io.github.mmc1234.jfreetype.image;
    exports io.github.mmc1234.jfreetype.system;
    exports io.github.mmc1234.jfreetype.util;
    exports io.github.mmc1234.jfreetype;
    exports io.github.mmc1234.jfreetype.glyph;
    exports io.github.mmc1234.jfreetype.easyfont;
    exports io.github.mmc1234.jfreetype.struct;
}