package ua.rDev.myEng.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ua.rDev.myEng.R;
import ua.rDev.myEng.model.Region;

/**
 * Created by pk on 08.07.2017.
 */

public class RegionRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<Region> data;
    Context context;

    public void addItem(Region region) {
        data.add(region);
        notifyItemInserted(0);
    }

    class RegionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView region_iv;
        TextView region_tv;

        public RegionHolder(View itemView) {
            super(itemView);
            this.region_iv = (ImageView) itemView.findViewById(R.id.region_iv);
            this.region_tv = (TextView) itemView.findViewById(R.id.region_tv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }

    public RegionRecyclerViewAdapter(ArrayList<Region> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.region_item, parent, false);
        return new RegionHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(context).load(data.get(position).getPhotoUrl()).fit().into(((RegionHolder) holder).region_iv);
        ((RegionHolder) holder).region_tv.setText(data.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
