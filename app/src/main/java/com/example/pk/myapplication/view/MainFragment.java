package com.example.pk.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.MainRecyclerViewAdapter;
import com.example.pk.myapplication.presenter.MainFragmentPresenter;

/**
 * Created by pk on 08.10.2016.
 */
public class MainFragment extends MvpAppCompatFragment implements IMainFragment {
    @InjectPresenter
    MainFragmentPresenter presenter;
    RecyclerView mainRecyclerView;
    MainRecyclerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_fragment, container, false);
        mainRecyclerView = (RecyclerView) v.findViewById(R.id.mainRecyclerView);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        return v;
    }

    @Override
    public void fillArray(String icon) {
        adapter = new MainRecyclerViewAdapter(getContext(), icon);
        mainRecyclerView.setAdapter(adapter);
    }
}
