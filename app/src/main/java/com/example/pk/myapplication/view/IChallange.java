package com.example.pk.myapplication.view;

import com.arellomobile.mvp.MvpView;
import com.example.pk.myapplication.model.Word;

import java.util.ArrayList;

/**
 * Created by pk on 22.05.2017.
 */

public interface IChallange extends MvpView {
    void showErorAlert();

    void showStartChallengeFragment();

    void showChallengeFragment();

    void showFinishChallangeFragment();

    void scrollToNextItem();

    void fillData(ArrayList<Word> arrayList);
}
