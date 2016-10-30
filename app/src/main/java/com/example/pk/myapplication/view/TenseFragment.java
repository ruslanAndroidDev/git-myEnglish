package com.example.pk.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.myapplication.R;

/**
 * Created by pk on 15.09.2016.
 */
public class TenseFragment extends Fragment implements View.OnClickListener {
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.tense_fragment, container, false);
        initTv();
        Log.d("tag", "OnCreateView Tense");
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Часи");
        return v;
    }

    TextView pastSimple;
    TextView presentSimple;
    TextView futureSimple;
    TextView futureInThePastSimple;
    TextView pastContinuous;
    TextView presentContinuous;
    TextView futureContinuous;
    TextView futureInThePastContinuous;
    TextView pastPerfect;
    TextView presentPerfect;
    TextView futurePerfect;
    TextView futureInThePastPerfect;
    TextView pastPerfectContinuous;
    TextView presentPerfectContinuous;
    TextView futurePerfectContinuous;
    TextView futureinthePastPerfectContinuous;

    private void initTv() {
        pastSimple = (TextView) v.findViewById(R.id.pastSimple);
        presentSimple = (TextView) v.findViewById(R.id.presentSimple);
        futureSimple = (TextView) v.findViewById(R.id.futureSimple);
        futureInThePastSimple = (TextView) v.findViewById(R.id.futureinthePastSimple);
        pastContinuous = (TextView) v.findViewById(R.id.pastContinuous);
        presentContinuous = (TextView) v.findViewById(R.id.presentContinuous);
        futureContinuous = (TextView) v.findViewById(R.id.futureContinuous);
        futureInThePastContinuous = (TextView) v.findViewById(R.id.futureinthePastContinuous);
        pastPerfect = (TextView) v.findViewById(R.id.pastPerfect);
        presentPerfect = (TextView) v.findViewById(R.id.presentPerfect);
        futurePerfect = (TextView) v.findViewById(R.id.futurePerfect);
        futureInThePastPerfect = (TextView) v.findViewById(R.id.futureinthePastPerfect);
        pastPerfectContinuous = (TextView) v.findViewById(R.id.pastPerfectContinuous);
        presentPerfectContinuous = (TextView) v.findViewById(R.id.presentPerfectContinuous);
        futurePerfectContinuous = (TextView) v.findViewById(R.id.futurePerfectContinuous);
        futureinthePastPerfectContinuous = (TextView) v.findViewById(R.id.futureinthePastPerfectContinuous);

        pastSimple.setOnClickListener(this);
        presentSimple.setOnClickListener(this);
        futureSimple.setOnClickListener(this);
        futureInThePastSimple.setOnClickListener(this);
        pastContinuous.setOnClickListener(this);
        presentContinuous.setOnClickListener(this);
        futureContinuous.setOnClickListener(this);
        futureInThePastContinuous.setOnClickListener(this);
        pastPerfect.setOnClickListener(this);
        presentPerfect.setOnClickListener(this);
        futurePerfect.setOnClickListener(this);
        futureInThePastPerfect.setOnClickListener(this);
        pastPerfectContinuous.setOnClickListener(this);
        presentPerfectContinuous.setOnClickListener(this);
        futurePerfectContinuous.setOnClickListener(this);
        futureinthePastPerfectContinuous.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        WebActivity web;
        String url = "";
        switch (v.getId()) {
            case R.id.pastSimple:
                url = "http://easy-english.com.ua/past-simple/";
                break;
            case R.id.presentSimple:
                url = "http://easy-english.com.ua/present-simple/";
                break;
            case R.id.futureSimple:
                url = "http://easy-english.com.ua/future-simple/";
                break;
            case R.id.futureinthePastSimple:
                url = "http://easy-english.com.ua/future-simple-in-the-past/";
                break;
            case R.id.pastContinuous:
                url = "http://easy-english.com.ua/past-continuous/";
                break;
            case R.id.presentContinuous:
                url = "http://easy-english.com.ua/present-continuous/";
                break;
            case R.id.futureContinuous:
                url = "http://easy-english.com.ua/future-continuous/";
                break;
            case R.id.futureinthePastContinuous:
                url = "http://easy-english.com.ua/future-continuous-in-the-past/";
                break;
            case R.id.pastPerfect:
                url = "http://easy-english.com.ua/past-perfect/";
                break;
            case R.id.presentPerfect:
                url = "http://easy-english.com.ua/present-perfect/";
                break;
            case R.id.futurePerfect:
                url = "http://easy-english.com.ua/future-perfect/";
                break;
            case R.id.futureinthePastPerfect:
                url = "http://easy-english.com.ua/future-perfect-in-the-past/";
                break;
            case R.id.pastPerfectContinuous:
                url = "http://easy-english.com.ua/past-perfect-continuous/";
                break;
            case R.id.presentPerfectContinuous:
                url = "http://easy-english.com.ua/present-perfect-continuous/";
                break;
            case R.id.futurePerfectContinuous:
                url = "http://easy-english.com.ua/future-perfect-continuous/";
                break;
            case R.id.futureinthePastPerfectContinuous:
                url = "http://easy-english.com.ua/future-perfect-continuous-in-the-past/";
                break;
        }
        Intent intent = new Intent(getActivity(),WebActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }
}
