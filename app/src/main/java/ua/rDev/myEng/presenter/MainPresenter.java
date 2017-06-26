package ua.rDev.myEng.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import ua.rDev.myEng.view.MainView;

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

    public void onVerbsItemSelect(String url) {
        getViewState().openWebActivity(url);
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
