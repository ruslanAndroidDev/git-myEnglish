package com.example.pk.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.Utill;
import com.example.pk.myapplication.presenter.ChallangePresenter;
import com.example.pk.myapplication.presenter.PagerResultListener;

/**
 * Created by pk on 23.09.2016.
 */
public class FinishChallengeFragment extends Fragment {
    static TextView int_tv;
    ImageView resultimg;

    ChallangePresenter challangePresenter;

    public FinishChallengeFragment(ChallangePresenter challangePresenter) {
        this.challangePresenter = challangePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish_challenge_fragment, container, false);
        int_tv = (TextView) v.findViewById(R.id.tv_Intresult);
        resultimg = (ImageView) v.findViewById(R.id.result_imageview);
        challangePresenter.setListener(new PagerResultListener() {
            @Override
            public void onUpdate(int true_item_count) {
                setResultImage(challangePresenter.getResult());
                int_tv.setText(true_item_count + "/" + challangePresenter.getNum_of_challange_item());
            }
        });
        return v;
    }

    private void setResultImage(double rating) {
        if (rating < 40) {
            resultimg.setImageBitmap(Utill.loadBitmapFromResource(getResources(),R.drawable.bad,300,300));
        } else if (rating > 40 && rating < 70) {
            resultimg.setImageBitmap(Utill.loadBitmapFromResource(getResources(),R.drawable.good,300,300));
        } else if (rating > 70) {
            resultimg.setImageBitmap(Utill.loadBitmapFromResource(getResources(),R.drawable.very_well,300,300));
        }
    }
}
