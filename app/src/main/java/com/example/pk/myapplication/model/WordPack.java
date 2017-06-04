package com.example.pk.myapplication.model;

import java.util.ArrayList;

/**
 * Created by pk on 02.06.2017.
 */

public class WordPack {
    String name;
    String photoUrl;
    ArrayList<String> wordsOriginal;
    ArrayList<String> wordsTranslate;
    int view;

    public WordPack(String name, String photoUrl, ArrayList<String> wordsOriginal, ArrayList<String> wordsTranslate) {
        this.name = name;
        this.photoUrl = photoUrl;
        this.wordsOriginal = wordsOriginal;
        this.wordsTranslate = wordsTranslate;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ArrayList<String> getWordsOriginal() {
        return wordsOriginal;
    }

    public ArrayList<String> getWordsTranslate() {
        return wordsTranslate;
    }

    public WordPack(String name, String photoUrl) {
        this.name = name;
        this.photoUrl = photoUrl;
    }

    public WordPack(String name, int photo) {
        this.name = name;
        this.view = photo;
    }

    public WordPack() {


    }
}
