package ua.rDev.myEng.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import ua.rDev.myEng.view.IMainFragment;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by pk on 01.06.2017.
 */
@InjectViewState
public class MainFragmentPresenter extends MvpPresenter<IMainFragment> {
    DatabaseReference reference;

    public MainFragmentPresenter() {
    }
}
