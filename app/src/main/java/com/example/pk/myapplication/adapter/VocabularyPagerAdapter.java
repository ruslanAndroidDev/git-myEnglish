package com.example.pk.myapplication.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.pk.myapplication.Constants;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.view.Challange_item_fragment;
import com.example.pk.myapplication.view.ChallengeViewPagerActivity;
import com.example.pk.myapplication.view.FinishChallengeFragment;

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

    public VocabularyPagerAdapter(FragmentManager fm, int num_of_words, Context context, ArrayList<Word> arrayList) {
        super(fm);
        this.num_of_words = num_of_words;
        dbSize = MyDataBaseHelper.getSize(context);
        wordArrayList = arrayList;
        random = new Random();
    }

    @Override
    public Fragment getItem(int position) {
        if (position==num_of_words){
            return new FinishChallengeFragment();
        }
        else{
            return new Challange_item_fragment();
        }
    }

    @Override
    public int getCount() {
        return num_of_words+1;
    }
}
