package com.example.pk.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.model.WordPack;
import com.squareup.picasso.Picasso;

/**
 * Created by pk on 15.06.2017.
 */

public class PanelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_BTN = -1;
    private static final int TYPE_ITEM = 1;
    WordPack wordPack;
    Context context;

    public PanelAdapter(WordPack wordPack, Context context) {
        this.wordPack = wordPack;
        this.context = context;
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        ImageView header_iv;

        public HeaderHolder(View itemView) {
            super(itemView);
            this.header_iv = (ImageView) itemView.findViewById(R.id.panel_iv);
        }
    }

    class WordHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RadioButton rb;
        TextView word_tv;
        TextView translate_word_tv;

        public WordHolder(View itemView) {
            super(itemView);
            this.rb = (RadioButton) itemView.findViewById(R.id.panel_rb);
            this.word_tv = (TextView) itemView.findViewById(R.id.panel_word);
            this.translate_word_tv = (TextView) itemView.findViewById(R.id.panel_translate_word);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            rb.setChecked(true);
        }
    }

    class ButtonHolder extends RecyclerView.ViewHolder {
        public ButtonHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.panel_header, parent, false);
            return new HeaderHolder(view);
        } else if (viewType == TYPE_BTN) {
            return null;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.panel_word, parent, false);
            return new WordHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof PanelAdapter.HeaderHolder) {
            ImageView header_iv = ((HeaderHolder) holder).header_iv;
            Picasso.with(context).load(wordPack.getPhotoUrl()).into(header_iv);
        } else {
            TextView word = ((WordHolder) holder).word_tv;
            TextView translate_word = ((WordHolder) holder).translate_word_tv;

            word.setText(wordPack.getWordsOriginal().get(position -1));
        }

    }

    @Override
    public int getItemCount() {
        return wordPack.getWordsOriginal().size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }
}
