package com.example.pk.myapplication.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pk.myapplication.R;
import com.example.pk.myapplication.Utill;
import com.example.pk.myapplication.adapter.MyListRecyclerAdapter;
import com.example.pk.myapplication.adapter.PanelAdapter;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.model.WordPack;
import com.example.pk.myapplication.presenter.VocabularyPresenter;
import com.mancj.slideup.SlideUp;

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
    View slideView;
    SlideUp slideUp;
    RecyclerView panel_rv;

    ImageView vocab_iv;
    TextView vocab_tv;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_vocabulary_frag, container, false);

        slideView = v.findViewById(R.id.slideView);
        slideUp = new SlideUp.Builder(slideView)
                .withStartState(SlideUp.State.HIDDEN)
                .withStartGravity(Gravity.TOP)
                .build();
        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        panel_rv = (RecyclerView) v.findViewById(R.id.panelRecyclerView);
        panel_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerv);

        vocab_iv = (ImageView) v.findViewById(R.id.vocab_iv);
        vocab_tv = (TextView) v.findViewById(R.id.vocab_tv);
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
        if (alertDialog == null) {
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
        }
        dialog_et_native.setText("");
        dialog_et_translate.setText("");

        alertDialog.show();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadData(getContext());
    }

    @Override
    public void showData(ArrayList<Word> data) {
        adapter = new MyListRecyclerAdapter(data, presenter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        if (data.size() == 0) {
            vocab_iv.setImageBitmap(Utill.loadBitmapFromResource(getResources(), R.drawable.clear_book, 250, 250));
            vocab_iv.setVisibility(View.VISIBLE);
            vocab_tv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void insertWord(String translate, String original, int status) {
        MyListRecyclerAdapter.data.add(0, new Word(translate, original, status));
        adapter.notifyItemInserted(0);
        vocab_iv.setVisibility(View.GONE);
        vocab_tv.setVisibility(View.GONE);
    }

    @Override
    public void showPanel(WordPack wordPack) {
        panel_rv.setAdapter(new PanelAdapter(wordPack, getContext(), presenter));
        slideUp.show();
        fab.hide();
    }

    @Override
    public void hidePanel() {
        slideUp.hide();
        fab.show();
    }

    public boolean isPanelShow() {
        return slideUp.isVisible();
    }
}
