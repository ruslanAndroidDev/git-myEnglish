package com.example.pk.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pk.myapplication.R;

/**
 * Created by pk on 14.10.2016.
 */
public class NoInternetConnectionFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.no_net_connection,container,false);
        return v;
    }
}
