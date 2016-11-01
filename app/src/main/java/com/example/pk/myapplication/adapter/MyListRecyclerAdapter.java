package com.example.pk.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;

import java.util.ArrayList;

/**
 * Created by pk on 09.09.2016.
 **/
public class MyListRecyclerAdapter extends RecyclerView.Adapter<MyListRecyclerAdapter.MyViewHolder> {
    public static ArrayList<Word> data;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView original_tv;
        TextView translate_tv;
        TextView position_tv;
        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.translate_tv = (TextView) itemView.findViewById(R.id.tv_vord_utranslate);
            this.original_tv = (TextView) itemView.findViewById(R.id.tv_vord_original);
            this.position_tv = (TextView) itemView.findViewById(R.id.position_tv);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            final String[] item_array = {"Видалити", "Редагувати"};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View customTitle = View.inflate(context, R.layout.customtitle, null);
            builder.setCustomTitle(customTitle);
            TextView tv = (TextView) customTitle.findViewById(R.id.customTitleTv);
            tv.setText(data.get(getAdapterPosition()).getOriginalWord());
            builder.setItems(item_array, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (item_array[item].equals("Видалити")) {
                        data.remove(getAdapterPosition());
                        MyDataBaseHelper.deleteItem(getAdapterPosition(), context);
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });
            builder.create();
            builder.show();
            return false;
        }
    }


    public MyListRecyclerAdapter(ArrayList<Word> data) {
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_word_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        TextView original_tv = holder.original_tv;
        final TextView translate_tv = holder.translate_tv;
        final TextView number_tv = holder.position_tv;

        original_tv.setText(data.get(position).getOriginalWord());
        translate_tv.setText(data.get(position).getTranslateWord());
        number_tv.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}
