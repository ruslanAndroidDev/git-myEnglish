package com.example.pk.myapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pk.myapplication.Constants;
import com.example.pk.myapplication.R;

/**
 * Created by pk on 23.09.2016.
 */
public class FinishChallengeFragment extends Fragment {
    static TextView int_tv;
    ImageView resultimg;

    private int result;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish_challenge_fragment, container, false);
        int_tv = (TextView) v.findViewById(R.id.tv_Intresult);
        resultimg = (ImageView) v.findViewById(R.id.result_imageview);
        setResultImage((double) Constants.TRUE_ANSWER / (double) Constants.NUM_OF_ITEM * 100);
        Log.d("tag", "true answer " + Constants.TRUE_ANSWER + " /" + Constants.NUM_OF_ITEM + "*100" + "=" + (double) Constants.TRUE_ANSWER / (double) Constants.NUM_OF_ITEM * 100);
        return v;
    }


    public static void updNumOfTrueItem() {
        try {
            int_tv.setText(Constants.TRUE_ANSWER + "/" + Constants.NUM_OF_ITEM);
        } catch (NullPointerException e) {
        }
    }

    private void setResultImage(double rating) {
        if (rating < 30) {
            resultimg.setImageResource(R.drawable.bad);
        } else if (rating > 30 && rating < 70) {
            resultimg.setImageResource(R.drawable.good);
        } else if (rating > 70) {
            resultimg.setImageResource(R.drawable.very_well);
        }

    }

}
