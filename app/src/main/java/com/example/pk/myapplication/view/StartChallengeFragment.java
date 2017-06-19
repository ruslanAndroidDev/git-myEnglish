package com.example.pk.myapplication.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.Utill;
import com.example.pk.myapplication.databinding.StartChallengeBinding;
import com.example.pk.myapplication.presenter.ChallangePresenter;

/**
 * Created by pk on 12.09.2016.
 */
public class StartChallengeFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    int num_of_challange_item;
    ChallangePresenter challangePresenter;
    StartChallengeBinding challengeBinding;

    public StartChallengeFragment(ChallangePresenter challangePresenter) {
        this.challangePresenter = challangePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        challengeBinding = DataBindingUtil.inflate(inflater, R.layout.start_challenge, container, false);
        challengeBinding.seekBar.setOnSeekBarChangeListener(this);
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
