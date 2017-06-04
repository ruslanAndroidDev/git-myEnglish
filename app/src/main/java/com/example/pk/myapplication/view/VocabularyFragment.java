package com.example.pk.myapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.MyListRecyclerAdapter;
import com.example.pk.myapplication.adapter.WordPackAdapter;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.model.WordPack;
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
    Context context;

    RecyclerView pack_recycler;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_vocabulary_frag, container, false);
        context = v.getContext();
        presenter.loadData(context);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerv);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        pack_recycler = (RecyclerView) v.findViewById(R.id.word_pack_recycler);
        pack_recycler.setLayoutManager(layoutManager);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = View.inflate(context, R.layout.dialogmaket, null);
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
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);

        ArrayList<WordPack> arrayList = new ArrayList<>();
        arrayList.add(new WordPack("car", "http://images.clipartpanda.com/fast-car-clipart-Car-Clip-art-01.png"));
        arrayList.add(new WordPack("relationShip", "http://images.clipartpanda.com/business-clipart-business-relationship-clipart-1.jpg"));
        arrayList.add(new WordPack("house", "http://images.clipartpanda.com/house-clipart-Houses-clip-art.png"));
        arrayList.add(new WordPack("City", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR4F8RzV1RHv1W56A1dnwT_IBIgbyB8bBeBVH4Wp1UmZMRwPno1"));
        WordPackAdapter wordPackAdapter = new WordPackAdapter(arrayList);
        pack_recycler.setAdapter(wordPackAdapter);
    }

    @Override
    public void insertWord(String translate, String original) {
        MyListRecyclerAdapter.data.add(0, new Word(translate, original));
        adapter.notifyItemInserted(0);
    }
}
