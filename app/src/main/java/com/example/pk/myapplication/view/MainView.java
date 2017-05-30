package com.example.pk.myapplication.view;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.SingleStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by pk on 22.05.2017.
 */
@StateStrategyType(SingleStateStrategy.class)
public interface MainView extends MvpView {
    void showMainFragment();

    void openWebActivity(String url);

    void showVocabluaryFragment();

    void showStartChallangeFragment();

    void showTenseFragment();
}
