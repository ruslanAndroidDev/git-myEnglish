package com.example.pk.myapplication.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.adapter.MyListRecyclerAdapter;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by pk on 09.09.2016.
 */
public class VocabularyFragment extends Fragment implements View.OnClickListener {
    ArrayList<Word> data;
    static RecyclerView recyclerView;
    static MyListRecyclerAdapter adapter;
    FloatingActionButton fab;
    Context context;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_vocabulary_frag, container, false);
        context = v.getContext();

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMyCustomDialog(inflater);
            }
        });

        data = MyDataBaseHelper.getAllWordwithDb(context);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerv);
        adapter = new MyListRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        ((Toolbar) getActivity().findViewById(R.id.toolbar)).setTitle("Мій словник");
        return v;
    }

    AlertDialog alertDialog;
    EditText dialog_et_native;
    EditText dialog_et_translate;
    Context mycontext;

    public void showMyCustomDialog(LayoutInflater inflater) {
        mycontext = context;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.dialog_maket, null);
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
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_button_ok) {
            if (dialog_et_native.getText().toString().equals("") || dialog_et_translate.getText().toString().equals("")) {
                alertDialog.dismiss();
            } else {
                MyDataBaseHelper.writetodb(mycontext, dialog_et_translate.getText().toString(), dialog_et_native.getText().toString());
                MyListRecyclerAdapter myListRecyclerAdapter = VocabularyFragment.adapter;
                MyListRecyclerAdapter.data.add(0, new Word(dialog_et_translate.getText().toString(), dialog_et_native.getText().toString()));
                myListRecyclerAdapter.notifyItemInserted(0);
                alertDialog.dismiss();
            }
        } else if (v.getId() == R.id.dialog_button_cancel) {
            alertDialog.dismiss();
        }

    }
}
