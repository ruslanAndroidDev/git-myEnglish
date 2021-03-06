package ua.rDev.myEng.presenter;

import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import ua.rDev.myEng.data.MyDataBaseHelper;
import ua.rDev.myEng.model.Word;
import ua.rDev.myEng.view.IChallange;

import java.util.ArrayList;

/**
 * Created by pk on 22.05.2017.
 */
@InjectViewState
public class ChallangePresenter extends MvpPresenter<IChallange> {
    private int num_of_challange_item;
    private int num_of_true_item;
    PagerResultListener listener;
    Context context;

    public void setListener(PagerResultListener listener) {
        this.listener = listener;
    }

    public ChallangePresenter() {
        getViewState().showStartChallengeFragment();
        clearData();
    }

    public int getNum_of_challange_item() {
        return num_of_challange_item;
    }

    public void addTrueItemCount() {
        ++num_of_true_item;
    }

    public int getNum_of_true_item() {
        return num_of_true_item;
    }

    public void loadData(Context context) {
        MyDataBaseHelper.loadWordwithDb(context, new MyDataBaseHelper.DataLoadListener() {
            @Override
            public void onLoad(ArrayList<Word> words) {
                if (words.size() < 10) {
                    getViewState().showErorAlert();
                } else {
                    getViewState().showChallengeLayout(num_of_challange_item, words);
                }
            }
        });
    }

    public void startChallange(Context context, int num_of_challange_item) {
        this.context = context;
        this.num_of_challange_item = num_of_challange_item;
        loadData(context);
    }

    public double getResult() {
        return (double) num_of_true_item / (double) num_of_challange_item * 100;
    }

    public void clearData() {
        num_of_challange_item = 0;
        num_of_true_item = 0;
    }

    public void updateResult() {
        listener.onUpdate(num_of_true_item);
    }

    public void setStatus(int status, String word) {
        MyDataBaseHelper.setStatus(context, status, word);
    }
}

