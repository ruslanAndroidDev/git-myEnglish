package com.example.pk.myapplication.presenter;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.example.pk.myapplication.view.MainFragment;
import com.example.pk.myapplication.view.MainView;
import com.example.pk.myapplication.view.StartChallengeFragment;
import com.example.pk.myapplication.view.TenseFragment;
import com.example.pk.myapplication.view.VocabularyFragment;
import com.example.pk.myapplication.view.WebActivity;

/**
 * Created by pk on 22.05.2017.
 */
@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {
    VocabularyFragment vocabularyFragment;
    MainFragment mainFragment;
    TenseFragment tenseFragment;

    WebActivity webActivity;
    StartChallengeFragment startChallengeFragment;

    public MainPresenter() {
        getViewState().showMainFragment();
    }

    public void vocabluaryItemSelected() {
        Log.d("tag", "vocabluaryItemSelected");
        //  getViewState().setToolbarTitle("Мій словник");
        if (vocabularyFragment == null)
            vocabularyFragment = new VocabularyFragment();
        //  getViewState().showFragment(vocabularyFragment, true);
    }

    public void mainFragmentItemSelected() {
        // getViewState().setToolbarTitle("My English");
        getViewState().showMainFragment();
    }

    public void tenseFragmentItemSelected() {
        //   getViewState().setToolbarTitle("Часи");
        if (tenseFragment == null)
            tenseFragment = new TenseFragment();
        //   getViewState().showFragment(tenseFragment, false);
    }

    public void startChallengeFragmentItemSelected() {
        //   getViewState().setToolbarTitle("English");
        if (startChallengeFragment == null)
            startChallengeFragment = new StartChallengeFragment();
        //  getViewState().showFragment(startChallengeFragment, false);
    }

    public void irgVerbsItemSelected() {
        getViewState().openWebActivity("http://easy-english.com.ua/irregular-verbs/");
    }
    //    private boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//        NetworkInfo activeNttworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNttworkInfo != null && activeNttworkInfo.isConnected();
//    }
}
