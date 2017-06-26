package ua.rDev.myEng.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.presenter.ChallangePresenter;
import ua.rDev.myEng.presenter.PagerResultListener;

/**
 * Created by pk on 23.09.2016.
 */
public class FinishChallengeFragment extends Fragment {
    static TextView int_tv;
    ImageView resultimg;

    ChallangePresenter challangePresenter;
    InterstitialAd interstitialAd;

    @SuppressLint("ValidFragment")
    public FinishChallengeFragment() {
    }

    @SuppressLint("ValidFragment")
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
        Random r = new Random(2);
        if (r.nextInt() == 0) {
            interstitialAd = new InterstitialAd(getContext());
            interstitialAd.setAdUnitId(getResources().getString(R.string.interstial_id));
            AdRequest adRequest = new AdRequest.Builder().build();
            interstitialAd.loadAd(adRequest);
            interstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    interstitialAd.show();
                }
            });
        }

        return v;
    }

    private void setResultImage(double rating) {
        if (rating < 40) {
            resultimg.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.bad, 300, 300));
        } else if (rating > 40 && rating < 70) {
            resultimg.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.good, 300, 300));
        } else if (rating > 70) {
            resultimg.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.very_well, 300, 300));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
