package com.example.pk.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.model.Country;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by pk on 17.06.2017.
 */

public class CountryAdapter extends RecyclerView.Adapter {
    public ArrayList<Country> arrayList;
    Context context;

    public CountryAdapter(ArrayList<Country> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView title;
        TextView article;

        public ViewHolder(View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.country_iv);
            this.title = (TextView) itemView.findViewById(R.id.country_title);
            this.article = (TextView) itemView.findViewById(R.id.country_article);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView img = ((ViewHolder) holder).img;
        Picasso.with(context).load(arrayList.get(position).getPhotoUrl()).into(img);
        TextView title = ((ViewHolder) holder).title;
        title.setText(arrayList.get(position).getName());

        TextView article = ((ViewHolder) holder).article;
        article.setText(arrayList.get(position).getArticle());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
