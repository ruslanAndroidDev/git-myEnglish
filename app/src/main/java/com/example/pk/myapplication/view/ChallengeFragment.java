package com.example.pk.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.example.pk.myapplication.Constants;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.VocabularyPagerAdapter;
import com.example.pk.myapplication.model.Word;

import java.util.ArrayList;

public class ChallengeFragment extends MvpAppCompatFragment implements View.OnClickListener {
    static MyCustomPager viewPager;
    VocabularyPagerAdapter vocabularyPagerAdapter;
    Button finishBtn;
    static int current_item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_repeat, container, false);
        finishBtn = (Button) v.findViewById(R.id.finish_button);
        finishBtn.setOnClickListener(this);
        viewPager = (MyCustomPager) v.findViewById(R.id.viewpager);
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_button:
                if (viewPager.getCurrentItem() != Constants.NUM_OF_ITEM) {
                    viewPager.setCurrentItem(Constants.NUM_OF_ITEM);
                    FinishChallengeFragment.onfinish();
                } else {
                    getActivity().finish();
                }
                break;
        }
    }

    public void scrollToNextItem() {
        viewPager.setCurrentItem(++current_item);
    }

    public void fillData(ArrayList<Word> arrayList) {
        vocabularyPagerAdapter = new VocabularyPagerAdapter(getFragmentManager(), Constants.NUM_OF_ITEM, getContext(), arrayList);
        viewPager.setAdapter(vocabularyPagerAdapter);
    }
}

