package com.example.pk.myapplication.presenter;

import android.content.Context;
import android.util.Log;

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
    static ArrayList<Word> words;

    public VocabularyPresenter() {
    }

    public void loadData(Context context) {
        MyDataBaseHelper.loadWordwithDb(context, new MyDataBaseHelper.DataLoadListener() {

            @Override
            public void onLoad(ArrayList<Word> words) {
                getViewState().showData(words);
                VocabularyPresenter.words = words;
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

    public void hidePanel() {
        getViewState().hidePanel();
    }

    public void addWordToBd(WordPack wordPack) {
        for (int i = wordPack.getSize() - 1; i >= 0; i--) {
            if (wordPack.getWordsOriginal(i).isCheck()) {
                if (isUnique(wordPack.getWordsOriginal(i).getWord(), wordPack)) {
                    MyDataBaseHelper.writetodb(context, wordPack.getWordsTranslate(i), wordPack.getWordsOriginal(i).getWord());
                    getViewState().insertWord(wordPack.getWordsTranslate(i), wordPack.getWordsOriginal(i).getWord(), MyDataBase.STATUS_UNKNOWN);
                }
            }
        }
    }

    private boolean isUnique(String word, WordPack wordPack) {
        for (int i = 0; i < words.size(); i++) {
            if (word.equals(words.get(i).getOriginalWord())) {
                return false;
            }
        }
        return true;
    }
}
