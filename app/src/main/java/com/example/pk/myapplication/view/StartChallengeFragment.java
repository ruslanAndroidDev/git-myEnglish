package com.example.pk.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.presenter.ChallangePresenter;

/**
 * Created by pk on 12.09.2016.
 */
public class StartChallengeFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    Button repeatBtn;
    SeekBar seekBar;
    int num_of_challange_item;
    TextView tv_numItem;
    ChallangePresenter challangePresenter;

    public StartChallengeFragment(ChallangePresenter challangePresenter) {
        this.challangePresenter = challangePresenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.start_challenge_fragment, container, false);
        tv_numItem = (TextView) v.findViewById(R.id.tv_num_item);

        seekBar = (SeekBar) v.findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(this);

        repeatBtn = (Button) v.findViewById(R.id.start_repeat);
        repeatBtn.setOnClickListener(this);
        return v;
    }


    @Override
    public void onClick(View v) {
        num_of_challange_item = Integer.parseInt(tv_numItem.getText().toString());
        challangePresenter.startChallange(num_of_challange_item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        tv_numItem.setText("" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
