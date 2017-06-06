package com.example.pk.myapplication.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pk.myapplication.data.MyDataBase;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;
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
        ArrayList<Word> data = MyDataBaseHelper.loadWordwithDb(context);
        getViewState().showData(data);
        this.context = context;
        //TODO: винести обробку даних в окремий потік
    }

    public void writeWord(String translate, String nativeWord) {
        MyDataBaseHelper.writetodb(context, translate, nativeWord);
        getViewState().insertWord(translate, nativeWord, MyDataBase.STATUS_UNKNOWN);
    }
}
