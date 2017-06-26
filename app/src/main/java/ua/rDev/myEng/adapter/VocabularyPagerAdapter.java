package ua.rDev.myEng.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ua.rDev.myEng.model.Word;
import ua.rDev.myEng.presenter.ChallangePresenter;
import ua.rDev.myEng.view.Challange_item_fragment;
import ua.rDev.myEng.view.FinishChallengeFragment;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pk on 17.09.2016.
 */
public class VocabularyPagerAdapter extends FragmentPagerAdapter {
    int num_of_words;
    int dbSize;
    Random random;
    ArrayList<Word> wordArrayList;
    ChallangePresenter challangePresenter;

    public VocabularyPagerAdapter(FragmentManager fm, int num_of_words, ArrayList<Word> arrayList, ChallangePresenter challangePresenter) {
        super(fm);
        this.num_of_words = num_of_words;
        dbSize = arrayList.size();
        wordArrayList = arrayList;
        this.challangePresenter = challangePresenter;
        random = new Random();
    }

    @Override
    public Fragment getItem(int position) {
        if (position == num_of_words) {
            return new FinishChallengeFragment(challangePresenter);
        } else {
            return new Challange_item_fragment(challangePresenter);
        }
    }

    //Last fragments is FinishChallengeFragment,so num_of_items+1
    @Override
    public int getCount() {
        return num_of_words + 1;
    }
}
