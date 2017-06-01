package com.example.pk.myapplication.presenter;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pk.myapplication.view.MainView;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by pk on 22.05.2017.
 */
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    public MainPresenter() {
        getViewState().showMainFragment();
    }

    public void onMainFragmentSelect() {
        getViewState().showMainFragment();
    }

    public void onVerbsItemSelect() {
        getViewState().openWebActivity("http://easy-english.com.ua/irregular-verbs/");
    }

    public void onVocabluaryItemSelect() {
        getViewState().showVocabluaryFragment();
    }

    public void onStartChallengeFragmentSelect() {
        getViewState().showStartChallangeFragment();
    }

    public void onTenseFragmentSelect() {
        getViewState().showTenseFragment();
    }
}
