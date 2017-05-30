package com.example.pk.myapplication.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.MyListRecyclerAdapter;
import com.example.pk.myapplication.databinding.DialogmaketBinding;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.presenter.VocabularyPresenter;

import java.util.ArrayList;

/**
 * Created by pk on 09.09.2016.
 */
public class VocabularyFragment extends Fragment implements View.OnClickListener, IVocabulary {
    @InjectPresenter
    VocabularyPresenter presenter;
    static RecyclerView recyclerView;
    static MyListRecyclerAdapter adapter;
    FloatingActionButton fab;
    Context context;
    LayoutInflater inflater;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_vocabulary_frag, container, false);
        context = v.getContext();
        this.inflater = inflater;
        presenter.loadData(context);

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
    DialogmaketBinding maketBinding;

    @Override
    public void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.dialogmaket, null);
        builder.setView(dialogView);

        maketBinding = DataBindingUtil.setContentView(getActivity(), R.layout.dialogmaket);
        maketBinding.setClicker(this);
        dialog_et_native = maketBinding.dialogEtOriginal;
        dialog_et_translate = maketBinding.dialogEtTranslate;
        alertDialog = builder.create();

        alertDialog.show();
    }

    @Override
    public void showData(ArrayList<Word> data) {
        adapter = new MyListRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    public void insertWord(String translate, String original) {
        MyListRecyclerAdapter.data.add(0, new Word(translate, original));
        adapter.notifyItemInserted(0);
    }
}
