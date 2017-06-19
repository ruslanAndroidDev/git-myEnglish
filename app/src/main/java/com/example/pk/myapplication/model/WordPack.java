package com.example.pk.myapplication.model;

import java.util.ArrayList;

/**
 * Created by pk on 02.06.2017.
 */

public class WordPack {
    public String name;
    public String icon;
    public ArrayList<String> words;
    public ArrayList<String> wordsTranslate;

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setWords(ArrayList<String> words) {
        this.words = words;
    }

    public void setWordsTranslate(ArrayList<String> wordsTranslate) {
        this.wordsTranslate = wordsTranslate;
    }

    public void setClicableWords(ArrayList<ClicableWord> clicableWords) {
        this.clicableWords = clicableWords;
    }

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

    public void deleteWords(int position) {
        clicableWords.remove(position);
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
