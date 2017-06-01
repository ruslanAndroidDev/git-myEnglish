package com.example.pk.myapplication.model;

import java.util.List;

/**
 * Created by pk on 01.06.2017.
 */

public class Model {
    String icon;
    List<String> words;

    public String getIcon() {
        return icon;
    }

    public Model() {
    }

    public Model(String icon, List words) {
        this.icon = icon;
        this.words = words;
    }

    public List getName() {
        return words;
    }
}
