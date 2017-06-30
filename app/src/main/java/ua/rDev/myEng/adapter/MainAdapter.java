package ua.rDev.myEng.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.model.MainItem;
import ua.rDev.myEng.view.ChallengeActivity;
import ua.rDev.myEng.view.CountryActivity;
import ua.rDev.myEng.view.SettingsActivity;
import ua.rDev.myEng.view.TenseActivity;
import ua.rDev.myEng.view.VocabularyActivity;
import ua.rDev.myEng.view.WebActivity;

/**
 * Created by pk on 26.06.2017.
 */

public class MainAdapter extends RecyclerView.Adapter {
    ArrayList<MainItem> arrayList;
    Context context;
    Listen listen;

    public abstract static class Listen {
        public abstract void onClick(int position);
    }

    public MainAdapter(Context context, Listen listen) {
        this.context = context;
        arrayList = new ArrayList<>();
        this.listen = listen;
        arrayList.add(new MainItem(context.getString(R.string.main_item), R.drawable.ic_mainpage_24dp));
        arrayList.add(new MainItem(context.getString(R.string.my_dictionary), R.drawable.ic_dictionary_24dp));
        arrayList.add(new MainItem(context.getString(R.string.english_tense), R.drawable.castle));
        arrayList.add(new MainItem(context.getString(R.string.iregular_verbs), R.drawable.book));
        arrayList.add(new MainItem(context.getString(R.string.repeat_word), R.drawable.ic_cached_black_24dp));
        arrayList.add(new MainItem(context.getString(R.string.action_settings), R.drawable.ic_build_black_24dp));
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView title;
        CardView main_cardView;

        public ItemHolder(View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.main_img);
            this.title = (TextView) itemView.findViewById(R.id.main_title);
            this.main_cardView = (CardView) itemView.findViewById(R.id.main_cardView);
            itemView.setOnClickListener(this);
        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onClick(View view) {
            listen.onClick(getAdapterPosition());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView main_img = ((ItemHolder) holder).img;
        main_img.setImageResource(arrayList.get(position).getRes());
        TextView textView = ((ItemHolder) holder).title;
        textView.setText(arrayList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
