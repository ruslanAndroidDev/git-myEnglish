package ua.rDev.myEng.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.model.WordPack;
import ua.rDev.myEng.presenter.VocabularyPresenter;

/**
 * Created by pk on 02.06.2017.
 */

public class WordPackAdapter extends RecyclerView.Adapter<WordPackAdapter.WordViewHolder> {
    ArrayList<WordPack> wordPacks;
    Context context;
    VocabularyPresenter presenter;
    int color;

    public class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView pack_photo;
        TextView pack_name;
        CardView cardView;

        public WordViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.pack_photo = (ImageView) itemView.findViewById(R.id.pack_photo);
            this.pack_name = (TextView) itemView.findViewById(R.id.pack_name);
            this.cardView = (CardView) itemView.findViewById(R.id.card);
            itemView.setOnClickListener(this);
            if (Utill.getThemeAccentColor(itemView.getContext()) == ContextCompat.getColor(itemView.getContext(), R.color.colorAccent)){
                color = R.color.card_brown3;
            } else{
                color = R.color.card_blue2;
            }
        }

        @Override
        public void onClick(View view) {
            presenter.onWordPackClick(wordPacks.get(getAdapterPosition()));
        }
    }


    public WordPackAdapter(ArrayList<WordPack> wordPacks, VocabularyPresenter presenter) {
        this.wordPacks = wordPacks;
        this.presenter = presenter;

    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_pack_item, parent, false);
        WordPackAdapter.WordViewHolder myViewHolder = new WordPackAdapter.WordViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final WordPackAdapter.WordViewHolder holder, final int position) {
        ImageView pack_photo = holder.pack_photo;
        final TextView pack_name = holder.pack_name;
        CardView cardView = holder.cardView;
        cardView.setCardBackgroundColor(ContextCompat.getColor(context, color));
        Picasso.with(context).load(wordPacks.get(position).getPhotoUrl()).into(pack_photo);
        pack_name.setText(wordPacks.get(position).getName());

        ViewCompat.setTransitionName(holder.pack_photo, pack_name.getText().toString());
    }

    @Override
    public int getItemCount() {
        return wordPacks.size();
    }
}
