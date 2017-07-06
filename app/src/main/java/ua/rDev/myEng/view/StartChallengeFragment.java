package ua.rDev.myEng.view;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.databinding.StartChallengeBinding;
import ua.rDev.myEng.presenter.ChallangePresenter;

/**
 * Created by pk on 12.09.2016.
 */
public class StartChallengeFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    int num_of_challange_item;
    ChallangePresenter challangePresenter;
    StartChallengeBinding challengeBinding;

    @SuppressLint("ValidFragment")
    public StartChallengeFragment() {
    }

    public StartChallengeFragment(ChallangePresenter challangePresenter) {
        this.challangePresenter = challangePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        challengeBinding = DataBindingUtil.inflate(inflater, R.layout.start_challenge, container, false);
        challengeBinding.seekBar.setOnSeekBarChangeListener(this);
        if (Utill.getThemeAccentColor(getContext()) == ContextCompat.getColor(getContext(), R.color.colorAccent2)) {
            challengeBinding.textView.setTextColor(ContextCompat.getColor(getContext(), R.color.countryTextcolor));
            challengeBinding.textView2.setTextColor(ContextCompat.getColor(getContext(), R.color.secondTextcolor));
            challengeBinding.textView4.setTextColor(ContextCompat.getColor(getContext(), R.color.secondTextcolor));
            challengeBinding.textView5.setTextColor(ContextCompat.getColor(getContext(), R.color.secondTextcolor));
            challengeBinding.textView6.setTextColor(ContextCompat.getColor(getContext(), R.color.secondTextcolor));
            challengeBinding.tvNumItem.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.card_blue1));
        }
        challengeBinding.setClicker(this);
        challengeBinding.challangeIv.setImageBitmap(
                Utill.loadBitmapFromResource(getResources(), R.drawable.book_5, 200, 200));
        return challengeBinding.getRoot();
    }


    @Override
    public void onClick(View v) {
        num_of_challange_item = Integer.parseInt(challengeBinding.tvNumItem.getText().toString());
        if (num_of_challange_item == 0) {
            Toast.makeText(getContext(), "Виберіть кількість слів!", Toast.LENGTH_SHORT).show();
        } else {
            challangePresenter.startChallange(getContext(), num_of_challange_item);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        challengeBinding.tvNumItem.setText("" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
