package io.github.mmc1234.jfreetype.example;

import io.github.mmc1234.jfreetype.core.FreeType;

public class Main {

    public static void main(String[] args) {
        FreeType.load();
        ExampleVersion.main(new String[0]);
        ExampleFace.main(new String[0]);
    }
}