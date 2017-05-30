package com.example.pk.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pk.myapplication.R;

/**
 * Created by pk on 08.10.2016.
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MyViewHolder> {
   // ArrayList<VkPosts> postsdata;
    Context context;

   // public MainRecyclerViewAdapter(ArrayList<VkPosts> postsdata, Context context) {
    //    this.context = context;
     //   this.postsdata = postsdata;
    //}

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView main_itemItv;
        Context context;
        ImageView main_frag_imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.main_itemItv = (TextView) itemView.findViewById(R.id.main_itemItv);
            this.main_frag_imageView = (ImageView) itemView.findViewById(R.id.main_item_imageView);


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
        final TextView main_itemItv = holder.main_itemItv;
        final ImageView main_frag_imageView = holder.main_frag_imageView;
     //   main_itemItv.setText(postsdata.get(position).getVktext());
        try {
     //       Picasso.with(context).load(postsdata.get(position).getHightphotoUrl()).placeholder(R.drawable.bad).into(main_frag_imageView);
        } catch (IllegalArgumentException ex) {
            main_frag_imageView.setImageResource(R.drawable.bad);
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //   @Override
    //public int getItemCount() {
    //    return postsdata.size();
    //}

}


