package ua.rDev.myEng.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.model.Country;
import ua.rDev.myEng.view.CountryDetailActivity;

/**
 * Created by pk on 17.06.2017.
 */

public class CountryAdapter extends RecyclerView.Adapter {
    public ArrayList<Country> arrayList;
    Context context;
    int colorAccent;

    public CountryAdapter(ArrayList<Country> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
        if (Utill.getThemeAccentColor(context) == ContextCompat.getColor(context, R.color.colorAccent2)) {
            colorAccent = R.color.colorAccent2;
        } else {
            colorAccent = R.color.colorAccent;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img;
        TextView title;
        TextView article;

        public ViewHolder(View itemView) {
            super(itemView);
            this.img = (ImageView) itemView.findViewById(R.id.country_iv);
            this.title = (TextView) itemView.findViewById(R.id.country_title);
            this.article = (TextView) itemView.findViewById(R.id.country_article);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, CountryDetailActivity.class);
            intent.putExtra("name", arrayList.get(getAdapterPosition()).getKey());
            context.startActivity(intent);
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
        Picasso.with(context).load(arrayList.get(position).getPhotoUrl()).fit().into(img);
        TextView title = ((ViewHolder) holder).title;
        TextView article = ((ViewHolder) holder).article;
        if (colorAccent == R.color.colorAccent) {
            title.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            article.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            title.setTextColor(ContextCompat.getColor(context, R.color.countryTextcolor));
            article.setTextColor(ContextCompat.getColor(context, R.color.secondTextcolor));
        }
        title.setText(arrayList.get(position).getName());
        try {
            article.setText(Html.fromHtml(arrayList.get(position).getIntroHtml()));
        } catch (NullPointerException e) {

        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
