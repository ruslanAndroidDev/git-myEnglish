package com.example.pk.myapplication.model;

import java.util.ArrayList;

/**
 * Created by pk on 02.06.2017.
 */

public class WordPack {
    String name;
    String icon;
    ArrayList<String> wordsOriginal;
    ArrayList<String> wordsTranslate;
    public WordPack(String name, String icon, ArrayList<String> wordsOriginal, ArrayList<String> wordsTranslate) {
        this.name = name;
        this.icon = icon;
        this.wordsOriginal = wordsOriginal;
        this.wordsTranslate = wordsTranslate;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return icon;
    }

    public ArrayList<String> getWordsOriginal() {
        return wordsOriginal;
    }

    public ArrayList<String> getWordsTranslate() {
        return wordsTranslate;
    }

    public WordPack(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }

    public WordPack() {
    }
}
