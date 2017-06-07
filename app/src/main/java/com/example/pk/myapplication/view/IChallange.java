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

    void showChallengeLayout(int num_of_challange_item,ArrayList<Word> arrayList);
}
