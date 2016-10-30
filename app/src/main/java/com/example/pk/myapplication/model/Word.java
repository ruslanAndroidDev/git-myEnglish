package com.example.pk.myapplication.model;

/**
 * Created by pk on 10.09.2016.
 */
public class Word {
    private String translateWord;
    private String originalWord;

    public Word(String translateWord, String originalWord) {
        this.translateWord = translateWord;
        this.originalWord = originalWord;
    }

    public String getTranslateWord() {
        return translateWord;
    }

    public String getOriginalWord() {
        return originalWord;
    }
}
