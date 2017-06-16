package com.example.pk.myapplication.view;

import com.arellomobile.mvp.MvpView;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.model.WordPack;

import java.util.ArrayList;

/**
 * Created by pk on 22.05.2017.
 */

public interface IVocabulary extends MvpView {
    void  showDialog();
    void showData(ArrayList<Word> data);
    void insertWord(String translate,String original,int status);
    void showPanel(WordPack wordPack);
}
