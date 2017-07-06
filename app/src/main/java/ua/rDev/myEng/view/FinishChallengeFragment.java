package ua.rDev.myEng.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class FinishChallengeFragment extends Fragment implements View.OnClickListener {
    static TextView int_tv;
    ImageView resultimg;

    ChallangePresenter challangePresenter;
    InterstitialAd interstitialAd;
    Button finishBtn;

    public FinishChallengeFragment(ChallangePresenter challangePresenter) {
        this.challangePresenter = challangePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.finish_challenge_fragment, container, false);
        int_tv = (TextView) v.findViewById(R.id.tv_Intresult);
        resultimg = (ImageView) v.findViewById(R.id.result_imageview);
        finishBtn = (Button) v.findViewById(R.id.finish_btn);
        finishBtn.setOnClickListener(this);
        challangePresenter.setListener(new PagerResultListener() {
            @Override
            public void onUpdate(int true_item_count) {
                setResultImage(challangePresenter.getResult());
                int_tv.setText(true_item_count + "/" + challangePresenter.getNum_of_challange_item());
            }
        });
        Random r = new Random();
        int randomValue = r.nextInt(2);
        if (randomValue == 0) {
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
        } else if (rating >= 40 && rating < 70) {
            resultimg.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.good, 300, 300));
        } else if (rating >= 70) {
            resultimg.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.very_well, 300, 300));
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        ActivityOptions options =
                ActivityOptions.makeCustomAnimation(getContext(), R.anim.anim_enter_to_main, R.anim.anim_leave_to_main);
        startActivity(intent, options.toBundle());
        getActivity().finish();
    }
}
