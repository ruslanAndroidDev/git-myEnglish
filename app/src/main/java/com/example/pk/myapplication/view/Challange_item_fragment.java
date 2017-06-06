package com.example.pk.myapplication.view;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.myapplication.Constants;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Variant;
import com.example.pk.myapplication.model.Word;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by pk on 17.09.2016.
 */
public class Challange_item_fragment extends Fragment implements View.OnClickListener {
    Context context;
    TextView tv_question_word;
    TextView trueItem;
    Random myRandom;

    SleepTask mySleepTask;
    Variant wordsVariant;

    String trueAnswerString;
    String questionString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySleepTask = new SleepTask();
        myRandom = new Random();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.challange_item, container, false);
        context = v.getContext();
        initializeItem(v);
        wordsVariant = buildDataSet();
        tv_question_word = (TextView) v.findViewById(R.id.tv_question_word);
        tv_question_word.setText(questionString);
        Log.d("tag", "questionString " + questionString);
        setFalse_item(wordsVariant);
        setTrue_item();
        return v;
    }

    private void setTrue_item() {
        int random = myRandom.nextInt(4);
        if (random == 0) {
            trueItem = tv_variant1;
        } else if (random == 1) {
            trueItem = tv_variant2;
        } else if (random == 2) {
            trueItem = tv_variant3;
        } else if (random == 3) {
            trueItem = tv_variant4;
        } else {
            trueItem = null;
        }
        trueItem.setText(trueAnswerString);
    }

    private void setFalse_item(Variant variant) {
        tv_variant1.setText(variant.getFalseVariant1());
        tv_variant2.setText(variant.getFalseVariant2());
        tv_variant3.setText(variant.getFalseVariant3());
        tv_variant4.setText(variant.getFalseVariant4());

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_cardView1:
                testAnswer(tv_variant1, cardView1);
                break;
            case R.id.item_cardView2:
                testAnswer(tv_variant2, cardView2);
                break;
            case R.id.item_cardView3:
                testAnswer(tv_variant3, cardView3);
                break;
            case R.id.item_cardView4:
                testAnswer(tv_variant4, cardView4);
                break;
        }


    }

    private void testAnswer(TextView tv, CardView cardView) {
        if (tv.getText().toString().equals(trueAnswerString)) {
            cardView.setCardBackgroundColor(Color.GREEN);
            Constants.TRUE_ANSWER++;
        } else {
            cardView.setCardBackgroundColor(Color.RED);
        }
        cardView1.setClickable(false);
        cardView2.setClickable(false);
        cardView3.setClickable(false);
        cardView4.setClickable(false);
        mySleepTask.execute();
    }

    TextView tv_variant1;
    TextView tv_variant2;
    TextView tv_variant3;
    TextView tv_variant4;

    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    CardView cardView4;

    public void initializeItem(View view) {
        tv_variant1 = (TextView) view.findViewById(R.id.tv_variant1);
        tv_variant2 = (TextView) view.findViewById(R.id.tv_variant2);
        tv_variant3 = (TextView) view.findViewById(R.id.tv_variant3);
        tv_variant4 = (TextView) view.findViewById(R.id.tv_variant4);

        cardView1 = (CardView) view.findViewById(R.id.item_cardView1);
        cardView2 = (CardView) view.findViewById(R.id.item_cardView2);
        cardView3 = (CardView) view.findViewById(R.id.item_cardView3);
        cardView4 = (CardView) view.findViewById(R.id.item_cardView4);

        cardView1.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
    }

    private int dB_size;
    ArrayList<Word> data;
    Word keyWord;
    Variant variant;

    private String falseVariant1;
    private String falseVariant2;
    private String falseVariant3;
    private String falseVariant4;

    public Variant buildDataSet() {
        data = MyDataBaseHelper.loadWordwithDb(context);
        dB_size = data.size();
        keyWord = data.get(myRandom.nextInt(dB_size));
        trueAnswerString = keyWord.getTranslateWord();
        questionString = keyWord.getOriginalWord();
        falseVariant1 = data.get(myRandom.nextInt(dB_size)).getTranslateWord();
        falseVariant2 = data.get(myRandom.nextInt(dB_size)).getTranslateWord();
        falseVariant3 = data.get(myRandom.nextInt(dB_size)).getTranslateWord();
        falseVariant4 = data.get(myRandom.nextInt(dB_size)).getTranslateWord();
        variant = new Variant(falseVariant1, falseVariant2, falseVariant3, falseVariant4);
        return variant;
    }
}

class SleepTask extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        ChallengeActivity.scrollToNext();

    }
}
