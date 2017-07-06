package ua.rDev.myEng.view;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.Random;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.data.MyDataBase;
import ua.rDev.myEng.data.MyDataBaseHelper;
import ua.rDev.myEng.model.Variant;
import ua.rDev.myEng.model.Word;
import ua.rDev.myEng.presenter.ChallangePresenter;

/**
 * Created by pk on 17.09.2016.
 */
public class Challange_item_fragment extends Fragment implements View.OnClickListener {
    Random myRandom;

    @SuppressLint("ValidFragment")
    public Challange_item_fragment() {
    }


    SleepTask mySleepTask;

    String trueAnswerString;
    String questionString;

    ChallangePresenter challangePresenter;

    TextView trueItem;
    TextView tv_question_word;

    @SuppressLint("ValidFragment")
    public Challange_item_fragment(ChallangePresenter challangePresenter) {
        this.challangePresenter = challangePresenter;
    }

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
        initializeItem(v);
        tv_question_word = (TextView) v.findViewById(R.id.tv_question_word);
        tv_question_word.setTextColor(Utill.getThemeAccentColor(getContext()));
        buildDataSet();
        return v;
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

    private void setFalse_item(Variant variant) {
        tv_variant1.setText(variant.getFalseVariant1());
        tv_variant2.setText(variant.getFalseVariant2());
        tv_variant3.setText(variant.getFalseVariant3());
        tv_variant4.setText(variant.getFalseVariant4());

        setTrue_item();

    }

    private void setTrue_item() {
        int random = myRandom.nextInt(4);
        if (random == 0) {
            tv_variant1.setText(trueAnswerString);
        } else if (random == 1) {
            tv_variant2.setText(trueAnswerString);
        } else if (random == 2) {
            tv_variant3.setText(trueAnswerString);
        } else if (random == 3) {
            tv_variant4.setText(trueAnswerString);
        } else {
            trueItem = null;
        }
        Log.d("tag", "true item " + trueAnswerString + ", random =" + random);
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
            challangePresenter.addTrueItemCount();
            challangePresenter.setStatus(MyDataBase.STATUS_STUDIED, questionString);
        } else {
            challangePresenter.setStatus(MyDataBase.STATUS_NEED_TO_REPEAT, questionString);
            cardView.setCardBackgroundColor(Color.RED);
        }
        cardView1.setClickable(false);
        cardView2.setClickable(false);
        cardView3.setClickable(false);
        cardView4.setClickable(false);
        mySleepTask.execute();
    }

    private int dB_size;
    ArrayList<Word> data;
    Word keyWord;
    Variant variant;

    private String falseVariant1;
    private String falseVariant2;
    private String falseVariant3;
    private String falseVariant4;

    public void buildDataSet() {
        MyDataBaseHelper.loadWordwithDb(getContext(), new MyDataBaseHelper.DataLoadListener() {
            @Override
            public void onLoad(ArrayList<Word> words) {
                data = words;
                dB_size = data.size();
                keyWord = data.get(myRandom.nextInt(dB_size));
                trueAnswerString = keyWord.getTranslateWord();
                questionString = keyWord.getOriginalWord();
                falseVariant1 = data.get(myRandom.nextInt(dB_size)).getTranslateWord();
                falseVariant2 = data.get(myRandom.nextInt(dB_size)).getTranslateWord();
                falseVariant3 = data.get(myRandom.nextInt(dB_size)).getTranslateWord();
                falseVariant4 = data.get(myRandom.nextInt(dB_size)).getTranslateWord();
                variant = new Variant(falseVariant1, falseVariant2, falseVariant3, falseVariant4);

                tv_question_word.setText(questionString);
                Log.d("tag", "questionString" + questionString);

                setFalse_item(variant);
            }
        });
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
