package com.example.pk.myapplication.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.VocabularyPagerAdapter;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.presenter.ChallangePresenter;

import java.util.ArrayList;

/**
 * Created by pk on 30.05.2017.
 */

public class ChallengeActivity extends MvpAppCompatActivity implements View.OnClickListener, IChallange {
    @InjectPresenter
    ChallangePresenter challangePresenter;

    FrameLayout challangeFrame;
    FragmentTransaction fragmentTransaction;
    Toolbar ch_toolbar;
    RelativeLayout challangeRelative;

    static MyCustomPager viewPager;
    VocabularyPagerAdapter vocabularyPagerAdapter;
    Button finishBtn;
    static int current_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
        ch_toolbar = (Toolbar) findViewById(R.id.ch_toolbar);
        setSupportActionBar(ch_toolbar);
        ch_toolbar.setTitle("Challange");
        challangeRelative = (RelativeLayout) findViewById(R.id.challengeRelative);

        finishBtn = (Button) findViewById(R.id.finish_button);
        finishBtn.setOnClickListener(this);
        viewPager = (MyCustomPager) findViewById(R.id.viewpager);

        challangeFrame = (FrameLayout) findViewById(R.id.challengeFrame);

        showStartChallengeFragment();
    }

    public void showErorAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setTitle("Помилка");
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setCancelable(false);
        builder.setMessage("Для повторення слів Ваш словник має містити не менше 10 слів");
        builder.show();
    }

    public void showStartChallengeFragment() {
        showFragment(new StartChallengeFragment(challangePresenter));
    }

    private void showFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.challengeFrame, fragment);
        fragmentTransaction.commit();
        challangeRelative.setVisibility(View.GONE);
        challangeFrame.setVisibility(View.VISIBLE);
    }

    public void showChallengeFragment(int num_of_challange_item) {
        vocabularyPagerAdapter = new VocabularyPagerAdapter(getSupportFragmentManager(), num_of_challange_item, this, MyDataBaseHelper.loadWordwithDb(this));
        viewPager.setAdapter(vocabularyPagerAdapter);
        challangeRelative.setVisibility(View.VISIBLE);
        challangeFrame.setVisibility(View.GONE);
    }

    public void showFinishChallangeFragment() {
        showFragment(new FinishChallengeFragment());
    }

    @Override
    public void scrollToNextItem() {

    }

    public static void scrollToNext() {
        viewPager.setCurrentItem(++current_item);
    }

    @Override
    public void fillData(ArrayList<Word> arrayList) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish_button:
                finish();
                break;
        }
    }
}
