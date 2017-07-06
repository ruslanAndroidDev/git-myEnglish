package ua.rDev.myEng.adapter;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ua.rDev.myEng.R;
import ua.rDev.myEng.Utill;
import ua.rDev.myEng.data.MyDataBase;
import ua.rDev.myEng.data.MyDataBaseHelper;
import ua.rDev.myEng.data.SkyEngService;
import ua.rDev.myEng.data.SkyEngWord;
import ua.rDev.myEng.model.Word;
import ua.rDev.myEng.model.WordPack;
import ua.rDev.myEng.presenter.VocabularyPresenter;

/**
 * Created by pk on 09.09.2016.
 **/
public class MyListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BANNER = 10;
    public static ArrayList<Word> data;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    MediaPlayer mediaPlayer;

    ArrayList<WordPack> arrayList;

    VocabularyPresenter presenter;
    Retrofit retrofit;
    SkyEngService service;
    FirebaseDatabase mDatabase;

    public class BannerHolder extends RecyclerView.ViewHolder {
        AdView adView;


        public BannerHolder(View itemView) {
            super(itemView);
            this.adView = (AdView) itemView.findViewById(R.id.word_baner);
            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
        }

    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView original_tv;
        TextView translate_tv;
        CardView card_status;
        Context context;
        RelativeLayout relativeLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            this.card_status = (CardView) itemView.findViewById(R.id.card_status);
            this.translate_tv = (TextView) itemView.findViewById(R.id.tv_vord_utranslate);
            this.original_tv = (TextView) itemView.findViewById(R.id.tv_vord_original);
            this.relativeLayout = (RelativeLayout) itemView.findViewById(R.id.word_item_relative);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Call<ArrayList<SkyEngWord>> call = service.translateWord(data.get(getAdapterPosition() - 2).getOriginalWord());
            call.enqueue(new Callback<ArrayList<SkyEngWord>>() {

                @Override
                public void onResponse
                        (Call<ArrayList<SkyEngWord>> call, Response<ArrayList<SkyEngWord>> response) {
                    mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource("https:" + response.body().get(0).getMeanings().get(0).getSoundUrl());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (IndexOutOfBoundsException e) {
                        Toast.makeText(context, context.getResources().getString(R.string.no_name), Toast.LENGTH_SHORT).show();
                    }
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<SkyEngWord>> call, Throwable t) {
                    Toast.makeText(context, context.getString(R.string.no_connect), Toast.LENGTH_LONG).show();
                }
            });
        }

        @Override
        public boolean onLongClick(View view) {
            MyDataBaseHelper.setStatus(context, MyDataBase.STATUS_STUDING, data.get(getAdapterPosition() - 2).getOriginalWord());
            Word word = data.get(getAdapterPosition() - 2);
            word.setStatus(MyDataBase.STATUS_STUDING);
            data.remove(getAdapterPosition() - 2);
            data.add(0, word);
            notifyItemMoved(getAdapterPosition(), 2);
            notifyItemChanged(2);
            return true;
        }
    }

    public class HeaderHolder extends RecyclerView.ViewHolder {
        RecyclerView packRv;

        public HeaderHolder(View itemView) {
            super(itemView);
            this.packRv = (RecyclerView) itemView.findViewById(R.id.word_pack_recycler);
        }

    }


    public MyListRecyclerAdapter(ArrayList<Word> data, VocabularyPresenter presenter) {
        this.data = data;
        this.presenter = presenter;
        retrofit = new Retrofit.Builder()
                .baseUrl("https://dictionary.skyeng.ru/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(SkyEngService.class);
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getStatus() == MyDataBase.STATUS_STUDING) {
                Word word = data.get(i);
                data.remove(i);
                data.add(0, word);
            }
        }
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dictionary_word_item, parent, false);
            return new MyViewHolder(view);
        } else if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_rv, parent, false);
            return new HeaderHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_ad_item, parent, false);
            return new BannerHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            TextView original_tv = ((MyViewHolder) holder).original_tv;
            final TextView translate_tv = ((MyViewHolder) holder).translate_tv;
            final CardView card_status = ((MyViewHolder) holder).card_status;
            final RelativeLayout relativeLayout = ((MyViewHolder) holder).relativeLayout;
            if (Utill.getThemeAccentColor(holder.itemView.getContext()) == ContextCompat.getColor(holder.itemView.getContext(), R.color.colorPrimary2)) {
                relativeLayout.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.card_blue1));
                translate_tv.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.secondTextcolor));
            }
            card_status.setCardBackgroundColor(getColorStatus(data.get(position - 2).getStatus()));
            original_tv.setText(data.get(position - 2).getOriginalWord());
            translate_tv.setText(data.get(position - 2).getTranslateWord());
        } else if (holder instanceof HeaderHolder) {
            RecyclerView packRv = ((HeaderHolder) holder).packRv;
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
            packRv.setLayoutManager(layoutManager);
            arrayList = new ArrayList<>();
            final WordPackAdapter wordPackAdapter = new WordPackAdapter(arrayList, presenter);
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
        } else {
            final AdView adView = ((BannerHolder) holder).adView;
            final AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);
            adView.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    adView.setVisibility(View.VISIBLE);
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
        } else if (position == 1) {
            return TYPE_BANNER;
        } else return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return data.size() + 2;
    }
}
