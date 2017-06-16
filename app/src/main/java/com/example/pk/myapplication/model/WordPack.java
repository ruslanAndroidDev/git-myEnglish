package com.example.pk.myapplication.model;

import java.util.ArrayList;

/**
 * Created by pk on 02.06.2017.
 */

public class WordPack {
    String name;
    String icon;
    ArrayList<String> words;
    ArrayList<String> wordsTranslate;

    public int getSize() {
        return words.size();
    }

    ArrayList<ClicableWord> clicableWords;

    public WordPack(String name, String icon, ArrayList<String> wordsOriginal, ArrayList<String> wordsTranslate) {
        this.name = name;
        this.icon = icon;
        this.words = wordsOriginal;
        this.wordsTranslate = wordsTranslate;
    }

    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return icon;
    }

    public ClicableWord getWordsOriginal(int position) {
        if (clicableWords == null) {
            buildClicableWords();
        }
        return clicableWords.get(position);
    }

    private void buildClicableWords() {
        clicableWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            clicableWords.add(new ClicableWord(words.get(i)));
        }
    }

    public String getWordsTranslate(int position) {
        return wordsTranslate.get(position);
    }

    public WordPack() {
    }

}
