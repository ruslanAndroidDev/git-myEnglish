package com.example.pk.myapplication.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.view.IChallange;

import java.util.ArrayList;

/**
 * Created by pk on 22.05.2017.
 */
@InjectViewState
public class ChallangePresenter extends MvpPresenter<IChallange> {
    public void loadData(Context context) {
        ArrayList<Word> arrayList = MyDataBaseHelper.loadWordwithDb(context);
        if (arrayList.size() < 10) {
            getViewState().showErorAlert();
        } else {
            getViewState().fillData(arrayList);
        }
    }

    public void startChallange(int num_of_challange_item) {
        getViewState().showChallengeFragment(num_of_challange_item);
    }
}
