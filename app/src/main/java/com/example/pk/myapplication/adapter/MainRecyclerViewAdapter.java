package com.example.pk.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pk.myapplication.R;
import com.squareup.picasso.Picasso;

/**
 * Created by pk on 08.10.2016.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
    // ArrayList<VkPosts> postsdata;
    Context context;
    String url;

    public MainRecyclerViewAdapter(Context context, String url) {
        this.context = context;
        this.url = url;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        //        TextView main_itemItv;
        Context context;
        ImageView main_frag_imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
//            this.main_itemItv = (TextView) itemView.findViewById(R.id.main_itemItv);
            this.main_frag_imageView = (ImageView) itemView.findViewById(R.id.main_img);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        final TextView main_itemItv = holder.main_itemItv;
        final ImageView main_frag_imageView = holder.main_frag_imageView;
        //   main_itemItv.setText(postsdata.get(position).getVktext());
        try {
            Picasso.with(context).load(url).placeholder(R.drawable.bad).into(main_frag_imageView);
        } catch (IllegalArgumentException ex) {
            main_frag_imageView.setImageResource(R.drawable.bad);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}


