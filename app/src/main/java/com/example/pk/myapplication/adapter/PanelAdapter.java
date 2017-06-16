package com.example.pk.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.model.WordPack;
import com.example.pk.myapplication.presenter.VocabularyPresenter;
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

    VocabularyPresenter presenter;

    public PanelAdapter(WordPack wordPack, Context context, VocabularyPresenter presenter) {
        this.wordPack = wordPack;
        this.context = context;
        this.presenter = presenter;
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
            rb.setClickable(false);
            this.word_tv = (TextView) itemView.findViewById(R.id.panel_word);
            this.translate_word_tv = (TextView) itemView.findViewById(R.id.panel_translate_word);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (wordPack.getWordsOriginal(getAdapterPosition() - 1).isCheck()) {
                wordPack.getWordsOriginal(getAdapterPosition() - 1).setCheck(false);
                rb.setChecked(false);
            } else {
                wordPack.getWordsOriginal(getAdapterPosition() - 1).setCheck(true);
                rb.setChecked(true);
            }
        }
    }

    class ButtonHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button ok;
        Button cancel;

        public ButtonHolder(View itemView) {
            super(itemView);
            this.cancel = (Button) itemView.findViewById(R.id.btn_cancel);
            this.ok = (Button) itemView.findViewById(R.id.btn_ok);

            ok.setOnClickListener(this);
            cancel.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_cancel) {
                presenter.hidePanel();
            } else {
                presenter.addWordToBd(wordPack);
                presenter.hidePanel();
            }
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.panel_header, parent, false);
            return new HeaderHolder(view);
        } else if (viewType == TYPE_BTN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.panel_btn, parent, false);
            return new ButtonHolder(view);
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
        } else if (holder instanceof PanelAdapter.WordHolder) {
            TextView word = ((WordHolder) holder).word_tv;
            TextView translate_word = ((WordHolder) holder).translate_word_tv;
            RadioButton rb = ((WordHolder) holder).rb;
            rb.setChecked(wordPack.getWordsOriginal(position - 1).isCheck());
            word.setText(wordPack.getWordsOriginal(position - 1).getWord());
            translate_word.setText(wordPack.getWordsTranslate(position - 1));
        }

    }

    @Override
    public int getItemCount() {
        return wordPack.getSize() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position == wordPack.getSize()) {
            return TYPE_BTN;
        } else {
            return TYPE_ITEM;
        }
    }
}
