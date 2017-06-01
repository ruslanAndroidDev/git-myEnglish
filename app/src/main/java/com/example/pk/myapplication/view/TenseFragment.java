package com.example.pk.myapplication.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.databinding.TenseFragmentBinding;

/**
 * Created by pk on 15.09.2016.
 */
public class TenseFragment extends Fragment implements View.OnClickListener {
    View v;
    TenseFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.tense_fragment, container, false);
        binding.setClicker(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
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
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
