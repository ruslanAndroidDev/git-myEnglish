package com.example.pk.myapplication.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pk.myapplication.data.MyDataBase;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.model.WordPack;
import com.example.pk.myapplication.view.IVocabulary;

import java.util.ArrayList;

/**
 * Created by pk on 22.05.2017.
 */
@InjectViewState
public class VocabularyPresenter extends MvpPresenter<IVocabulary> {
    Context context;

    public VocabularyPresenter() {
    }

    public void loadData(Context context) {
        MyDataBaseHelper.loadWordwithDb(context, new MyDataBaseHelper.DataLoadListener() {
            @Override
            public void onLoad(ArrayList<Word> words) {
                getViewState().showData(words);
            }
        });
    }

    public void writeWord(String translate, String nativeWord) {
        MyDataBaseHelper.writetodb(context, translate, nativeWord);
        getViewState().insertWord(translate, nativeWord, MyDataBase.STATUS_UNKNOWN);
    }

    public void onWordPackClick(WordPack wordPack) {
        getViewState().showPanel(wordPack);
    }
}
