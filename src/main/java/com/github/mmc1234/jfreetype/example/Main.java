package com.github.mmc1234.jfreetype.example;

public class Main {

    public static void main(String[] args){
        System.setProperty("jfreetype.library", "D:\\freetype.dll");
        ExampleVersion.main(new String[0]);
        ExampleFace.main(new String[0]);
    }
}