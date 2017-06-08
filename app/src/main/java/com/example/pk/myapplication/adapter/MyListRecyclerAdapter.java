package com.example.pk.myapplication.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pk.myapplication.R;
import com.example.pk.myapplication.data.MyDataBaseHelper;
import com.example.pk.myapplication.model.Word;
import com.example.pk.myapplication.model.WordPack;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by pk on 09.09.2016.
 **/
public class MyListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static ArrayList<Word> data;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView original_tv;
        TextView translate_tv;
        CardView card_status;
        Context context;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.card_status = (CardView) itemView.findViewById(R.id.card_status);
            this.translate_tv = (TextView) itemView.findViewById(R.id.tv_vord_utranslate);
            this.original_tv = (TextView) itemView.findViewById(R.id.tv_vord_original);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            final String[] item_array = {"Видалити", "Редагувати"};
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View customTitle = View.inflate(context, R.layout.customtitle, null);
            builder.setCustomTitle(customTitle);
            TextView tv = (TextView) customTitle.findViewById(R.id.customTitleTv);
            tv.setText(data.get(getAdapterPosition() - 1).getOriginalWord());
            builder.setItems(item_array, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (item_array[item].equals("Видалити")) {
                        data.remove(getAdapterPosition() - 1);
                        MyDataBaseHelper.deleteItem(getAdapterPosition() - 1, context);
                        notifyItemRemoved(getAdapterPosition());
                    }
                }
            });
            builder.create();
            builder.show();
            return false;
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        RecyclerView packRv;

        public HeaderHolder(View itemView) {
            super(itemView);
            this.packRv = (RecyclerView) itemView.findViewById(R.id.word_pack_recycler);
        }
    }


    public MyListRecyclerAdapter(ArrayList<Word> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_word_item, parent, false);
            return new MyViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_rv, parent, false);
            return new HeaderHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            TextView original_tv = ((MyViewHolder) holder).original_tv;
            final TextView translate_tv = ((MyViewHolder) holder).translate_tv;
            final CardView card_status = ((MyViewHolder) holder).card_status;

            card_status.setCardBackgroundColor(getColorStatus(data.get(position - 1).getStatus()));
            original_tv.setText(data.get(position - 1).getOriginalWord());
            translate_tv.setText(data.get(position - 1).getTranslateWord());
        } else if (holder instanceof HeaderHolder) {
            RecyclerView packRv = ((HeaderHolder) holder).packRv;
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            packRv.setLayoutManager(layoutManager);
            final ArrayList<WordPack> arrayList = new ArrayList<>();
            final WordPackAdapter wordPackAdapter = new WordPackAdapter(arrayList);
            FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference reference = mDatabase.getReference("pack/");
            packRv.setAdapter(wordPackAdapter);
            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    WordPack model = dataSnapshot.getValue(WordPack.class);
                    arrayList.add(model);
                    wordPackAdapter.notifyItemInserted(0);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    int getColorStatus(int status) {
        if (status == 0) {
            return Color.GRAY;
        } else if (status == 1) {
            return Color.GREEN;
        } else {
            return Color.RED;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }


}
