package com.example.pk.myapplication.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.pk.myapplication.Constants;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.VocabularyPagerAdapter;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;

import java.util.ArrayList;

public class ChallengeViewPagerActivity extends AppCompatActivity implements View.OnClickListener {
    static MyCustomPager viewPager;
    VocabularyPagerAdapter vocabularyPagerAdapter;
    Button finishBtn;
    static int current_item;
    ArrayList<Word> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.TRUE_ANSWER = 0;
        current_item = 0;
        setContentView(R.layout.activity_repeat);
        finishBtn = (Button) findViewById(R.id.finish_button);
        finishBtn.setOnClickListener(this);
        viewPager = (MyCustomPager) findViewById(R.id.viewpager);
        arrayList = MyDataBaseHelper.getAllWordwithDb(this);
        if (reviseOk(this)) {
            vocabularyPagerAdapter = new VocabularyPagerAdapter(getSupportFragmentManager(), Constants.NUM_OF_ITEM, this, arrayList);
            viewPager.setAdapter(vocabularyPagerAdapter);
        } else {
            showErorAlert();
        }

    }


    private boolean reviseOk(Context context) {
        if (MyDataBaseHelper.getSize(context) < 10) {
            return false;
        } else return true;
    }

    // show when in vocabulary is less then 10 words
    private void showErorAlert() {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finish_button:
                if (viewPager.getCurrentItem() != Constants.NUM_OF_ITEM) {
                    viewPager.setCurrentItem(Constants.NUM_OF_ITEM);
                    FinishChallengeFragment.onfinish();
                } else {
                    finish();
                }
                break;
        }
    }

    public static void scrollToNextItem() {
        viewPager.setCurrentItem(++current_item);
    }

}

