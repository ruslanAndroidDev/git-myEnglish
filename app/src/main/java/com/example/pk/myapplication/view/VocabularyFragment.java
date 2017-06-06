package com.example.pk.myapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.MyListRecyclerAdapter;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.presenter.VocabularyPresenter;

import java.util.ArrayList;

/**
 * Created by pk on 09.09.2016.
 */
public class VocabularyFragment extends MvpAppCompatFragment implements View.OnClickListener, IVocabulary {
    @InjectPresenter
    VocabularyPresenter presenter;
    static RecyclerView recyclerView;
    static MyListRecyclerAdapter adapter;
    FloatingActionButton fab;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_vocabulary_frag, container, false);
        presenter.loadData(getContext());

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerv);
        return v;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_button_ok) {
            if (dialog_et_native.getText().toString().equals("") || dialog_et_translate.getText().toString().equals("")) {
                alertDialog.dismiss();
            } else {
                presenter.writeWord(dialog_et_translate.getText().toString(), dialog_et_native.getText().toString());
                alertDialog.dismiss();
            }
        } else if (v.getId() == R.id.dialog_button_cancel) {
            alertDialog.dismiss();
        }

    }

    AlertDialog alertDialog;
    EditText dialog_et_native;
    EditText dialog_et_translate;

    @Override
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = View.inflate(getContext(), R.layout.dialogmaket, null);
        builder.setView(dialogView);

        Button dialog_btn_ok = (Button) dialogView.findViewById(R.id.dialog_button_ok);
        Button dialog_btn_cancel = (Button) dialogView.findViewById(R.id.dialog_button_cancel);
        dialog_et_native = (EditText) dialogView.findViewById(R.id.dialog_et_original);
        dialog_et_translate = (EditText) dialogView.findViewById(R.id.dialog_et_translate);

        dialog_btn_cancel.setOnClickListener(this);
        dialog_btn_ok.setOnClickListener(this);
        alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    public void showData(ArrayList<Word> data) {
        adapter = new MyListRecyclerAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void insertWord(String translate, String original,int status) {
        MyListRecyclerAdapter.data.add(0, new Word(translate, original,status));
        adapter.notifyItemInserted(0);
    }
}
