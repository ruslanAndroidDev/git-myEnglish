package ua.rDev.myEng.view;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ua.rDev.myEng.R;
import ua.rDev.myEng.databinding.TenseFragmentBinding;

/**
 * Created by pk on 15.09.2016.
 */
public class TenseFragment extends Fragment implements View.OnClickListener {
    TenseFragmentBinding binding;
    String url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.tense_fragment, container, false);
        binding.setClicker(this);
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pastSimple:
                url = getResources().getString(R.string.past_simple_url);
                break;
            case R.id.presentSimple:
                url = getResources().getString(R.string.present_simple_url);
                break;
            case R.id.futureSimple:
                url = getResources().getString(R.string.future_simple_url);
                break;
            case R.id.futureinthePastSimple:
                url = getResources().getString(R.string.future_simple_in_the_past_url);
                break;
            case R.id.pastContinuous:
                url = getResources().getString(R.string.past_continuous_url);
                break;
            case R.id.presentContinuous:
                url = getResources().getString(R.string.present_continuous_url);
                break;
            case R.id.futureContinuous:
                url = getResources().getString(R.string.future_continuous_url);
                break;
            case R.id.futureinthePastContinuous:
                url = getResources().getString(R.string.future_continuous_in_the_past_url);
                break;
            case R.id.pastPerfect:
                url = getResources().getString(R.string.past_perfect_url);
                break;
            case R.id.presentPerfect:
                url = getResources().getString(R.string.present_perfect_url);
                break;
            case R.id.futurePerfect:
                url = getResources().getString(R.string.future_perfect_url);
                break;
            case R.id.futureinthePastPerfect:
                url = getResources().getString(R.string.future_perfect_in_the_past_url);
                break;
            case R.id.pastPerfectContinuous:
                url = getResources().getString(R.string.past_perfect_continuous_url);
                break;
            case R.id.presentPerfectContinuous:
                url = getResources().getString(R.string.present_perfect_continuous_url);
                break;
            case R.id.futurePerfectContinuous:
                url = getResources().getString(R.string.future_perfect_continuous_url);
                break;
            case R.id.futureinthePastPerfectContinuous:
                url = getResources().getString(R.string.future_perfect_continuous_in_the_past_url);
                break;
        }
        Intent intent = new Intent(getActivity(), WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
}
