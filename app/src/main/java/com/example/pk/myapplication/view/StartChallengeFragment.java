package com.example.pk.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pk.myapplication.Constants;
import com.example.pk.myapplication.R;

/**
 * Created by pk on 12.09.2016.
 */
public class StartChallengeFragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    Button repeatBtn;
    SeekBar seekBar;
    int num_of_challange_item;
    TextView tv_numItem;

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
        //TODO ПЕРЕПИСАТИ
//        Intent intent = new Intent(v.getContext(), ChallengeFragment.class);
//        num_of_challange_item = Integer.parseInt(tv_numItem.getText().toString());
//
//        Constants.NUM_OF_ITEM = num_of_challange_item;
//
//        if (Constants.NUM_OF_ITEM == 0) {
//            Toast.makeText(v.getContext(), "Помилка", Toast.LENGTH_SHORT).show();
//        } else {
//            startActivity(intent);
//        }
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
