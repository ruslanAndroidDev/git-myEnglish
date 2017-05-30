package com.example.pk.myapplication.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.presenter.ChallangePresenter;

import java.util.ArrayList;

/**
 * Created by pk on 30.05.2017.
 */

public class ChallengeActivity extends MvpAppCompatActivity implements IChallange {
    @InjectPresenter
    ChallangePresenter challangePresenter;
    FragmentTransaction fragmentTransaction;
    ChallengeFragment challengeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.challenge);
        showStartChallengeFragment();
    }

    @Override
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

    @Override
    public void showStartChallengeFragment() {
        showFragment(new StartChallengeFragment());
    }

    private void showFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.challengeFrame, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showChallengeFragment() {
        showFragment(new ChallengeFragment());
    }

    @Override
    public void showFinishChallangeFragment() {
        showFragment(new FinishChallengeFragment());
    }

    @Override
    public void scrollToNextItem() {
        challengeFragment.scrollToNextItem();
    }

    @Override
    public void fillData(ArrayList<Word> arrayList) {
        challengeFragment.fillData(arrayList);
    }
}
